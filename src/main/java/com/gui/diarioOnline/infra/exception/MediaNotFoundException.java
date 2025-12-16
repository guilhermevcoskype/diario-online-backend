package com.gui.diarioOnline.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MediaNotFoundException extends RuntimeException {
    public MediaNotFoundException() {
        super("Mídia não encontrada.");
    }
}

