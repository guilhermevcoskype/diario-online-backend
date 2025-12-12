package com.gui.diarioOnline.controller.dto;

public record UserMediaUpdateRequest(

        String email,
        MediaListUpdateRequest media
) {
}
