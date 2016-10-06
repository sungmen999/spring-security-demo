package com.example.model;

import lombok.Data;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Data
public class User {
    // User information fields
    private String username;
    private String password;
    private String passwordEncoder;

    // Spring Security fields
    private Set<Authority> authorities = new HashSet<>();
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
}
