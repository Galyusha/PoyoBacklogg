package com.spingweb.PoyoBacklog.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RenderingResponse;

import com.spingweb.PoyoBacklog.model.Game;
import com.spingweb.PoyoBacklog.model.RawgResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RawgService {
    private final WebClient webClient;
    private final String apiKey;
    
    public CompletableFuture<List<Game>> searchGames(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games")
                        .queryParam("search", query)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(RawgResponse.class)
                .map(RawgResponse::getResults)
                .toFuture();
    }
    
    public CompletableFuture<Game> getGameDetails(Long rawgId) {
        return webClient.get()
                .uri("/games/{id}?key={key}", rawgId, apiKey)
                .retrieve()
                .bodyToMono(Game.class)
                .toFuture();
    }
}
