package com.spingweb.PoyoBacklog.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.service.GameService;
import com.spingweb.PoyoBacklog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final GameService gameService;
    private final UserService userService;
    
    @GetMapping("/home")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/register";
        }
        User user = userService.getCurrentUser(principal);
        
        model.addAttribute("playingGames", 
            gameService.getGamesByUserAndStatus(user, Game.Status.PLAYING));
        model.addAttribute("completedGames", 
            gameService.getGamesByUserAndStatus(user, Game.Status.COMPLETED));
        model.addAttribute("backlogGames", 
            gameService.getGamesByUserAndStatus(user, Game.Status.BACKLOG));
        
        return "index";
    }
}