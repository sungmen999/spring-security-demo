package com.example.security.exception;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by sungmen999 on 10/11/2016 AD.
 */

public enum ErrorCode {
    GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);

    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
