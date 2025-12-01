package com.gui.diarioOnline.controller.dto;

import com.gui.diarioOnline.infra.model.Game;

public record SaveGameRequest(
        Long id,
        String name,
        String summary,
        String cover,
        Double rating,
        String comments,
        String email
) {
    public Game toModel() {
        return Game.builder()
                .gameId(id)
                .name(name)
                .summary(summary)
                .cover(cover)
                .rating(rating)
                .comments(comments)
                .build();
    }
}
