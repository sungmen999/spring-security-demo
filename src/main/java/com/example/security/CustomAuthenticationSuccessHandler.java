package com.example.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            response.sendRedirect(response.encodeURL("/helloAdmin"));
        }
        response.sendRedirect(response.encodeURL("/helloUser"));
    }
}
