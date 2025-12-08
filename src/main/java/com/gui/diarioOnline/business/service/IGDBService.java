package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.CoverResponse;
import com.gui.diarioOnline.controller.dto.DetailedGameResponse;
import com.gui.diarioOnline.controller.dto.GameResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class IGDBService {

    private final WebClient webClient;

    public IGDBService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<DetailedGameResponse> consumeGamesList(String game) {
        String queryGameList = "search \""+game+"\"; fields name, summary, cover;";
        return webClient.post()
                .uri("/games")
                .bodyValue(queryGameList)
                .retrieve()
                .bodyToFlux(GameResponse.class)
                .flatMap(gameResponse -> {

                    if (gameResponse.cover() == null) {
                        return Mono.just(new DetailedGameResponse(
                                gameResponse.id(),
                                gameResponse.name(),
                                gameResponse.summary(),
                                null,
                                "N/A"
                        ));
                    }

                    Mono<CoverResponse> coverMono = consumeCoverGame(gameResponse);

                    return coverMono.map(cover ->
                            new DetailedGameResponse(
                                    gameResponse.id(),
                                    gameResponse.name(),
                                    gameResponse.summary(),
                                    gameResponse.cover(),
                                    cover.url()
                            )
                    );
                }).collectList().block();
    }

    public Mono<CoverResponse> consumeCoverGame(GameResponse game) {
        String queryCoverUrl = "fields url; where id = "+game.cover()+";";
        return webClient.post()
                .uri("/covers")
                .bodyValue(queryCoverUrl)
                .retrieve()
                .bodyToFlux(CoverResponse.class).next();
    }

}
