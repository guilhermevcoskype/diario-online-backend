package com.gui.diarioOnline.controller.dto;

import com.gui.diarioOnline.infra.model.Role;

import java.util.List;

public record UserResponseDTO(

        String name,

        String email,

        List<GameResponseDTO>media,

        List<Role> roles
) {
}
