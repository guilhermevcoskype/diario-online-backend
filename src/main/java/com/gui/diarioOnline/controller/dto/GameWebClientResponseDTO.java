package com.gui.diarioOnline.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GameWebClientResponseDTO(
        Long id,
        String name,
        String summary,
        Long cover
) {}

