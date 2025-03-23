package com.spingweb.PoyoBacklog.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.service.GameService;
import com.spingweb.PoyoBacklog.service.UserService;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Game> getGames(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return gameService.getGamesByUser(user);
    }

    @PostMapping
    public Game addGame(@RequestBody Game game, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        game.setUser(user);
        return gameService.addGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game updatedGame) {
        return gameService.updateGame(id, updatedGame);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}