package com.spingweb.PoyoBacklog.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") @Validated User user,
            BindingResult result,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
    
        if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Passwords do not match");
        }
        
        if (userRepository.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already exists");
        }
        
        if (result.hasErrors()) {
            return "register";
        }
        

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        
        return "redirect:/login?registered";
    }
}