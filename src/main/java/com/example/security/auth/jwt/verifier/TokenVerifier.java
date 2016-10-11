package com.example.security.auth.jwt.verifier;

/**
 * Created by sungmen999 on 10/11/2016 AD.
 */
public interface TokenVerifier {
    public boolean verify(String jti);
}
