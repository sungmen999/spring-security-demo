package com.example.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be parsed
 *
 * Created by sungmen999 on 10/10/2016 AD.
 */
public class JwtTokenMalformedException extends AuthenticationException {
    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}
