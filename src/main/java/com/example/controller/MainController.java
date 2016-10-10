package com.example.controller;

import com.example.model.Greeting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sungmen999 on 10/10/2016 AD.
 */
@RestController
public class MainController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Greeting homePage(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
    @RequestMapping(value = {"/user", "/me"}, method = RequestMethod.POST)
    public ResponseEntity<?> user(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
