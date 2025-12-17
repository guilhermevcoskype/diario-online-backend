package com.gui.diarioOnline.controller.dto;

public record SaveMediaRequestDTO(
        MediaRequestDTO mediaRequestDTO,
        String email,
        Double rating,
        String comments
) {
}
