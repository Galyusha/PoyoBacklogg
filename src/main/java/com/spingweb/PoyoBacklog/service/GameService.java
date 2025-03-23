package com.spingweb.PoyoBacklog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.repository.GameRepository;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> getGamesByUser(User user) {
        return gameRepository.findByUser(user);
    }

    public Game updateGame(Long id, Game updatedGame) {
        Game game = gameRepository.findById(id).orElseThrow();
        game.setTitle(updatedGame.getTitle());
        game.setPlatform(updatedGame.getPlatform());
        game.setGenre(updatedGame.getGenre());
        game.setReleaseDate(updatedGame.getReleaseDate());
        game.setStatus(updatedGame.getStatus());
        return gameRepository.save(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
