package com.example.controller;

import com.example.security.model.UserContext;
import com.example.security.auth.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sungmen999 on 10/10/2016 AD.
 */
@RestController
public class MainController {
    @RequestMapping(value="/api/me", method=RequestMethod.GET)
    public @ResponseBody
    UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }
}
