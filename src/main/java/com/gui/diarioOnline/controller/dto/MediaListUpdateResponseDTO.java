package com.gui.diarioOnline.controller.dto;

public record MediaListUpdateResponseDTO(
        String id,
        String gameId,
        String name,
        String summary,
        String cover,
        Double rating,
        String comments
) {
}