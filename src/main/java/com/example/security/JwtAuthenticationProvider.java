package com.example.security;

import com.example.security.exception.JwtTokenMalformedException;
import com.example.security.model.AuthenticatedUser;
import com.example.security.model.JwtAuthenticationToken;
import com.example.security.transfer.JwtUserDto;
import com.example.security.util.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Class validates a given token by using the secret configured in the application
 *
 * Created by sungmen999 on 10/10/2016 AD.
 */

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final JwtTokenValidator jwtTokenValidator;

    @Autowired
    public JwtAuthenticationProvider(JwtTokenValidator jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        JwtUserDto parsedUser = jwtTokenValidator.parseToken(token);
        if (parsedUser == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());
        return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorityList);
    }
}
