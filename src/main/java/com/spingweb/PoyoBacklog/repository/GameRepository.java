package com.spingweb.PoyoBacklog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.Game.Status;
import com.spingweb.PoyoBacklog.model.User;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByUser(User user);
    List<Game> findByUserAndStatus(User user, Status status);
    boolean existsByUserAndTitle(User user, String title);
    long countByUserAndStatus(User user, Status status);
}