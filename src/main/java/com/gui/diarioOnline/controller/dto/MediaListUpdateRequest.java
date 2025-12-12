package com.gui.diarioOnline.controller.dto;

public record MediaListUpdateRequest(
        String gameId,
        String name,
        String summary,
        String cover,
        Double rating,
        String comments
) {
}