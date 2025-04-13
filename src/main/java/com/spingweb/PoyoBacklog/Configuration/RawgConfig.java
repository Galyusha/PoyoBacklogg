package com.spingweb.PoyoBacklog.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.spingweb.PoyoBacklog.service.RawgService;


@Configuration
public class RawgConfig {
    
    @Value("${rawg.api.key}")
    private String rawgApiKey;
    
    @Value("${rawg.api.url}")
    private String rawgApiUrl;
    
    @Bean
    public WebClient rawgWebClient() {
        return WebClient.builder()
                .baseUrl(rawgApiUrl)
                .defaultHeader("User-Agent", "GameTrackerApp")
                .build();
    }
    
    @Bean
    public RawgService rawgService(WebClient rawgWebClient) {
        return new RawgService(rawgWebClient, rawgApiKey);
    }
}