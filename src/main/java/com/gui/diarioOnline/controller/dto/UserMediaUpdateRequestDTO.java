package com.gui.diarioOnline.controller.dto;

public record UserMediaUpdateRequestDTO(

        String email,
        MediaListUpdateRequestDTO media
) {
}
