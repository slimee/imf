package com.eigaas.imf.web;

import com.eigaas.imf.exception.MissionNotFoundException;
import com.eigaas.imf.exception.SpyNotFoundException;
import com.eigaas.imf.security.jwt.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {MissionNotFoundException.class})
    public ResponseEntity missionNotFound(MissionNotFoundException ex, WebRequest request) {
        log.debug("handling MissionNotFoundException...");
        return notFound().build();
    }

    @ExceptionHandler(value = {SpyNotFoundException.class})
    public ResponseEntity spyNotFound(SpyNotFoundException ex, WebRequest request) {
        log.debug("handling MissionNotFoundException...");
        return notFound().build();
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return status(UNAUTHORIZED).build();
    }
}
