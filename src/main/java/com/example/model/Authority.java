package com.example.model;

import lombok.Data;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Data
public class Authority {
    private String name;

    public Authority(String name) {
        this.name = name;
    }
}
