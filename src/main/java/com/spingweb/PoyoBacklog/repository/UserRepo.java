package com.spingweb.PoyoBacklog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spingweb.PoyoBacklog.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
