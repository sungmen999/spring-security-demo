package com.example.security.auth.jwt.extractor;

/**
 * Implementations of this interface should always return raw base-64 encoded
 * representation of JWT Token.
 *
 * Created by sungmen999 on 10/11/2016 AD.
 */
public interface TokenExtractor {
    public String extract(String payload);
}
