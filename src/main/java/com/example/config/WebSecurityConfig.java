package com.example.config;

import com.example.security.CustomAuthenticationSuccessHandler;
import com.example.security.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
        http
            .exceptionHandling()
                .accessDeniedPage("/403");

        http.httpBasic();
    }

//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // AuthenticationProvider - inMemoryAuthentication
//        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsService();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customUserDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }


    @Bean
    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider());
    }
}
