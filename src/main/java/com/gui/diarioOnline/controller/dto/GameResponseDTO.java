package com.gui.diarioOnline.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GameResponseDTO(
        String id,
        String gameId,
        String name,
        String summary,
        String cover,
        Double rating,
        String comments
) {}

