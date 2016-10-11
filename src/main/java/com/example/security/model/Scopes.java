package com.example.security.model;

/**
 * Created by sungmen999 on 10/11/2016 AD.
 */
public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
