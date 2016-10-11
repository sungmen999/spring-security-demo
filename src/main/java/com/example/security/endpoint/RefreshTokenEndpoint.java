package com.example.security.endpoint;

import com.example.model.User;
import com.example.security.model.UserContext;
import com.example.security.auth.jwt.extractor.TokenExtractor;
import com.example.security.auth.jwt.verifier.TokenVerifier;
import com.example.security.config.JwtSettings;
import com.example.security.config.WebSecurityConfig;
import com.example.security.exception.InvalidJwtToken;
import com.example.security.model.token.JwtToken;
import com.example.security.model.token.JwtTokenFactory;
import com.example.security.model.token.RawAccessJwtToken;
import com.example.security.model.token.RefreshToken;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sungmen999 on 10/11/2016 AD.
 */

@RestController
public class RefreshTokenEndpoint {
    private final JwtTokenFactory tokenFactory;
    private final JwtSettings jwtSettings;
    private final UserService userService;
    private final TokenVerifier tokenVerifier;
    private final TokenExtractor tokenExtractor;

    @Autowired
    public RefreshTokenEndpoint(TokenVerifier tokenVerifier, JwtSettings jwtSettings, JwtTokenFactory tokenFactory, UserService userService, @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor) {
        this.tokenVerifier = tokenVerifier;
        this.jwtSettings = jwtSettings;
        this.tokenFactory = tokenFactory;
        this.userService = userService;
        this.tokenExtractor = tokenExtractor;
    }

    @RequestMapping(value="/api/auth/token", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public Map<String, String> refreshToken(HttpServletRequest request) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.findByUsername(subject);

        if(user == null) throw new UsernameNotFoundException("User not found: " + subject);
        if (user.getAuthorities() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        JwtToken access = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refresh = tokenFactory.createRefreshToken(userContext);

        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("accessToken", access.getToken());
        tokenMap.put("refreshToken", refresh.getToken());

        return tokenMap;
    }
}
