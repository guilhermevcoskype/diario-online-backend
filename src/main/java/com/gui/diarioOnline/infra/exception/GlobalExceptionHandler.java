package com.gui.diarioOnline.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Este método trata especificamente a EmailAlreadyUsedException
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Object> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MediaAlreadyExistsException.class)
    public ResponseEntity<Object> MediaAlreadyExistsException(MediaAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MediaWithUserNoException.class)
    public ResponseEntity<Object> mediaWithUserNoException(MediaWithUserNoException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.NO_CONTENT.value());
        body.put("error", "No Content");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UserWithNoMediaException.class)
    public ResponseEntity<Object> userWithNoMediaException(UserWithNoMediaException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.NO_CONTENT.value());
        body.put("error", "No Content");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> AuthenticationException(AuthenticationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
