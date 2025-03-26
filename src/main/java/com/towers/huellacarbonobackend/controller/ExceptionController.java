package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ErrorResponse;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(DataAccessExceptionImpl.class)
    public ResponseEntity<ResponseDto> dataAccessExceptionImpl(DataAccessExceptionImpl e) {
        String errorMessage = "DataAccessExceptionImpl: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Fallo al Acceder a los datos", e.getMessage());
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto> constraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessageTemplate();
            errors.put(fieldName, errorMessage);
        }
        String errorMessage = "ConstraintViolationException: Datos inválidos.";
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Datos inválidos", errors);
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.BAD_REQUEST);
    }
}
