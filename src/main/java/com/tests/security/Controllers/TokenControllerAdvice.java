package com.tests.security.Controllers;

import com.tests.security.Beans.response.MessageResponse;
import com.tests.security.Exceptions.RefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.messaging.support.ErrorMessage;

import java.util.Date;


@RestControllerAdvice
public class TokenControllerAdvice {
    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleTokenRefreshException(RefreshTokenException e) {
        return ResponseEntity.ok(new MessageResponse(e.getMessage()));
    }
}
