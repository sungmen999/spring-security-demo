package com.example.security.auth.jwt.verifier;

import org.springframework.stereotype.Component;

/**
 * Created by sungmen999 on 10/11/2016 AD.
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
