package com.spingweb.PoyoBacklog.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.repository.GameRepository;
import com.spingweb.PoyoBacklog.service.RawgService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final RawgService rawgService;
    private final GameRepository gameRepository;
    
    @GetMapping("/search")
    public String searchGames(@RequestParam String query, Model model) {
        try {
            List<Game> results = rawgService.searchGames(query).get();
            model.addAttribute("results", results);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to search games");
        }
        return "search-results";
    }
    
    @PostMapping("/add")
    public String addGame(@RequestParam Long rawgId, 
                         @AuthenticationPrincipal User user) {
        rawgService.getGameDetails(rawgId)
            .thenAccept(gameDetails -> {
                Game game = new Game();
                game.setTitle(gameDetails.getTitle());
                game.setPlatform(gameDetails.getPlatform());
                game.setGenre(gameDetails.getGenre());
                game.setReleaseDate(gameDetails.getReleaseDate());
                game.setImageUrl(gameDetails.getImageUrl());
                game.setHoursPlayed(gameDetails.getHoursPlayed());
                game.setUser(user);
                gameRepository.save(game);
            });
        
        return "redirect:/home";
    }
    
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, 
                             @RequestParam Game.Status status) {
        gameRepository.findById(id).ifPresent(game -> {
            game.setStatus(status);
            gameRepository.save(game);
        });
        return "redirect:/home";
    }
}