package com.spingweb.PoyoBacklog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spingweb.PoyoBacklog.exception.GameNotFoundException;
import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.Game.Status;
import com.spingweb.PoyoBacklog.model.User;
import com.spingweb.PoyoBacklog.repository.GameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {
    
    private final GameRepository gameRepository;

    public Game addGameToUser(User user, Game game) {
        game.setUser(user);
        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByUser(User user) {
        return gameRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByUserAndStatus(User user, Status status) {
        return gameRepository.findByUserAndStatus(user, status);
    }

    public Game updateGame(Long id, Game updatedGame) {
        return gameRepository.findById(id)
            .map(existingGame -> {
                existingGame.setTitle(updatedGame.getTitle());
                existingGame.setPlatform(updatedGame.getPlatform());
                existingGame.setGenre(updatedGame.getGenre());
                existingGame.setReleaseDate(updatedGame.getReleaseDate());
                existingGame.setStatus(updatedGame.getStatus());
                existingGame.setImageUrl(updatedGame.getImageUrl());
                existingGame.setHoursPlayed(updatedGame.getHoursPlayed());
                return gameRepository.save(existingGame);
            })
            .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
    }

    public Game updateGameStatus(Long id, Status newStatus) {
        return gameRepository.findById(id)
            .map(game -> {
                game.setStatus(newStatus);
                return gameRepository.save(game);
            })
            .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
    }

    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new GameNotFoundException("Game not found with id: " + id);
        }
        gameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean gameExistsForUser(User user, String gameTitle) {
        return gameRepository.existsByUserAndTitle(user, gameTitle);
    }

    @Transactional(readOnly = true)
    public long countGamesByUserAndStatus(User user, Status status) {
        return gameRepository.countByUserAndStatus(user, status);
    }
}