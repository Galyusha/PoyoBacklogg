package com.spingweb.PoyoBacklog.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getCurrentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }
}
