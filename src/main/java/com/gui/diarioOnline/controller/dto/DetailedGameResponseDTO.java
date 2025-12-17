package com.gui.diarioOnline.controller.dto;

public record DetailedGameResponseDTO(
        Long id,
        String name,
        String summary,
        Long coverId,
        String coverUrl
) {}