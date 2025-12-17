package com.gui.diarioOnline.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestLoginDTO(
        @Email @NotBlank String email,
        @NotBlank String password) {
}
