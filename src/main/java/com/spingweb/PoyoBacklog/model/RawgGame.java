package com.spingweb.PoyoBacklog.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class RawgGame {
    private Long id;
    private String name;
    private String background_image;
    private List<Genre> genres;
    private List<PlatformWrapper> platforms;
    private String released;

    @Data
    public static class Genre {
        private String name;
    }

    @Data
    public static class PlatformWrapper {
        private Platform platform;
    }

    @Data
    public static class Platform {
        private String name;
    }

    public Game toGame() {
        Game game = new Game();
        game.setId(this.id);
        game.setTitle(this.name);
        game.setImageUrl(this.background_image);
        game.setGenre(this.genres != null && !this.genres.isEmpty() ? this.genres.get(0).getName() : "Unknown");
        game.setPlatform(this.platforms != null && !this.platforms.isEmpty() ? this.platforms.get(0).getPlatform().getName() : "Unknown");
        game.setReleaseDate(this.released);
        return game;
    }
}