package com.gui.diarioOnline.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class MediaWithUserNoException extends RuntimeException {
    public MediaWithUserNoException() {
        super("Mídia sem usuario relacionado.");
    }
}

