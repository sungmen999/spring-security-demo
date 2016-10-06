package com.example.service;

import com.example.model.Authority;
import com.example.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Service
public class UserService {
    public User findByUsername(String username) {
        for (User user : users()) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    private List<User> users() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setUsername("user");
        user.setPassword("$2a$10$VXZqUE/NtI5NRl5DPpgJ6eM7O/lyE0UqlpHHKxDI9MTMP/.OJc92W");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.getAuthorities().add(new Authority("ROLE_USER"));

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("$2a$10$VXZqUE/NtI5NRl5DPpgJ6eM7O/lyE0UqlpHHKxDI9MTMP/.OJc92W");
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.getAuthorities().add(new Authority("ROLE_ADMIN"));

        users.add(user);
        users.add(admin);

        return users;
    }
}
