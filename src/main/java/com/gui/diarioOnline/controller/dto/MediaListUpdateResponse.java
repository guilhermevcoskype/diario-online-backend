package com.gui.diarioOnline.controller.dto;

public record MediaListUpdateResponse(
        String name,
        String summary,
        String cover,
        Double rating,
        String comments
) {
}