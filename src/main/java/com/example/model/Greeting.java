package com.example.model;

/**
 * Created by sungmen999 on 10/10/2016 AD.
 */
public class Greeting {
    private final long id;
    private final String content;
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
    public long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
}
