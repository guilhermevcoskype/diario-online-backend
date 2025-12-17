package com.gui.diarioOnline.controller.dto;

public record UserResponseLoginDTO(

        UserResponseDTO userResponseDTO,

        String accessToken
) {
}
