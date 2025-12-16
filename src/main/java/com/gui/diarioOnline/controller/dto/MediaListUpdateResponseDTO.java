package com.gui.diarioOnline.controller.dto;

public record MediaListUpdateResponseDTO(
        String id,
        String busenessId,
        String name,
        String summary,
        String cover,
        Double rating,
        String comments
) {
}