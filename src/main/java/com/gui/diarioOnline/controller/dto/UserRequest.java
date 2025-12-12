package com.gui.diarioOnline.controller.dto;

import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public record UserRequest (
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotBlank String password) {
    public User toModel() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .roles(List.of(Role.USER))
                .media(new ArrayList<>())
                .build();
    }
}
