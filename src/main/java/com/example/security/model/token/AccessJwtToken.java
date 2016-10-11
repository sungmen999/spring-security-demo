package com.example.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

/**
 * Created by sungmen999 on 10/11/2016 AD.
 */

public class AccessJwtToken implements JwtToken{
    private final String rawToken;
    @JsonIgnore private Claims claims;

    protected AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
