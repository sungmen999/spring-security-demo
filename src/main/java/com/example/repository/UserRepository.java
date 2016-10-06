package com.example.repository;

import com.example.model.Authority;
import com.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Repository
public class UserRepository {
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setUsername("user");
        user.setPasswordEncoder("$2a$10$VXZqUE/NtI5NRl5DPpgJ6eM7O/lyE0UqlpHHKxDI9MTMP/.OJc92W");
        user.setPassword("password");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.getAuthorities().add(new Authority("ROLE_USER"));

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("$2a$10$VXZqUE/NtI5NRl5DPpgJ6eM7O/lyE0UqlpHHKxDI9MTMP/.OJc92W");
        admin.setPassword("password");
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
