package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.CoverResponseDTO;
import com.gui.diarioOnline.controller.dto.DetailedGameResponseDTO;
import com.gui.diarioOnline.controller.dto.GameWebClientResponseDTO;
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

    public List<DetailedGameResponseDTO> consumeGamesList(String game) {
        String queryGameList = "search \""+game+"\"; fields name, summary, cover;";
        return webClient.post()
                .uri("/games")
                .bodyValue(queryGameList)
                .retrieve()
                .bodyToFlux(GameWebClientResponseDTO.class)
                .flatMap(gameWebClientResponseDTO -> {

                    if (gameWebClientResponseDTO.cover() == null) {
                        return Mono.just(new DetailedGameResponseDTO(
                                gameWebClientResponseDTO.id(),
                                gameWebClientResponseDTO.name(),
                                gameWebClientResponseDTO.summary(),
                                null,
                                "N/A"
                        ));
                    }

                    Mono<CoverResponseDTO> coverMono = consumeCoverGame(gameWebClientResponseDTO);

                    return coverMono.map(cover ->
                            new DetailedGameResponseDTO(
                                    gameWebClientResponseDTO.id(),
                                    gameWebClientResponseDTO.name(),
                                    gameWebClientResponseDTO.summary(),
                                    gameWebClientResponseDTO.cover(),
                                    cover.url()
                            )
                    );
                }).collectList().block();
    }

    public Mono<CoverResponseDTO> consumeCoverGame(GameWebClientResponseDTO game) {
        String queryCoverUrl = "fields url; where id = "+game.cover()+";";
        return webClient.post()
                .uri("/covers")
                .bodyValue(queryCoverUrl)
                .retrieve()
                .bodyToFlux(CoverResponseDTO.class).next();
    }

}
