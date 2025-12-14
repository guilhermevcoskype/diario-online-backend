package com.gui.diarioOnline.controller.dto;

public record MediaRequestDTO(
        String gameId,
        String name,
        String summary,
        String cover,
        String type
) {}