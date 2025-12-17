package com.gui.diarioOnline.controller.dto;

public record MediaRequestDTO(
        String id,
        String gameId,
        String name,
        String summary,
        String cover,
        String type
) {}