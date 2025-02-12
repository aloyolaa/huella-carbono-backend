package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ErrorResponse;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto> entityNotFoundException(EntityNotFoundException e) {
        String errorMessage = "EntityNotFoundException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Fallo en Búsqueda de Datos", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(errorResponse, false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String errorMessage = "MethodArgumentNotValidException: Datos incompletos.";
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Datos incompletos", errors);
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.BAD_REQUEST);
    }
}
