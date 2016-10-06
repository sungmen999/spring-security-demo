package com.example.service;

import com.example.model.Authority;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username) {
        for (User user : userRepository.getUsers()) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User findByUsernameAndPassword(String username, String password) {
        for (User user : userRepository.getUsers()) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
