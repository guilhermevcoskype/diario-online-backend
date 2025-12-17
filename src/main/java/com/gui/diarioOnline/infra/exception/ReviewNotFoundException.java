package com.gui.diarioOnline.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super("Review não encontrada.");
    }
}

