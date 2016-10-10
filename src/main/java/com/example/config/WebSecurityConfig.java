package com.example.config;

import com.example.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationProvider authenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Check URL
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll() // Anyone
                .antMatchers("/helloUser").hasRole("USER")
                .antMatchers("/helloAdmin").hasRole("ADMIN")
                .anyRequest().authenticated();

        // Handle form login
        // ถ้าไม่่กำหนด defaultSuccessUrl() หลังจาก login สำเร็จ จะวิ่งไปที่ "/"
        http
            .formLogin()
                .loginProcessingUrl("/perform_login")
                .loginPage("/login")
                .permitAll();

        // Handle logout
        http
            .logout()
                .permitAll();

        // Exception Handling
        http.exceptionHandling().accessDeniedPage("/403");

        // We don't need CSRF because our token is invulnerable
        http.csrf().disable();

        // Call our errorHandler
        http.exceptionHandling()
                .accessDeniedPage("/403") // Access Denied
                .authenticationEntryPoint(unauthorizedHandler); // authentication/authorisation fails

        // Don't create session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Custom JWT based security filter
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // Disable page caching
        http.headers().cacheControl();
    }
}
