package com.example.controller;

import com.example.model.LoginForm;
import com.example.security.AuthoritiesConstants;
import com.example.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

//    @GetMapping("/hello")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String home() throws IOException {
//        return "hello";
//    }

    @GetMapping("/login")
    public String showLoginForm(@ModelAttribute("loginForm") LoginForm loginForm) throws IOException {
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(HttpServletRequest request,
                               @Valid LoginForm loginForm,
                               BindingResult result) throws IOException {
        if(result.hasErrors()) {
            return "login";
        }

        try {

            // Authentication provider
            UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());
            usernamePasswordAuthentication.setDetails(new WebAuthenticationDetails(request));

            // loads the user and his authorities
            Authentication auth = authenticationManager.authenticate(usernamePasswordAuthentication);

            //  Store details of the present security context,
            //  and it is stored in the ThreadLocal class in Java enables you to create variables that can only be read and written by the same thread.
            // - Principal
            // - Authorities/Roles
            SecurityContext securityContext = SecurityContextHolder.getContext(); // A security context is established for the user
            securityContext.setAuthentication(auth);

            if (auth.isAuthenticated()) { // User is not anonymous (Role[USER/ADMIN])
                HttpSession session = request.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            } else { // User is anonymous
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:login?error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login?error";
        }

        // Redirect based on role
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return "redirect:/helloAdmin";
        }

        return "redirect:/helloUser";
    }
}
