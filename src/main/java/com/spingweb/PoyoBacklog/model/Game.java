package com.spingweb.PoyoBacklog.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String platform;
    private String genre;
    private LocalDate releaseDate;
    private String imageUrl;
    private Integer hoursPlayed;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public enum Status {
        PLAYING, COMPLETED, BACKLOG, DROPPED, WISHLIST
    }

}