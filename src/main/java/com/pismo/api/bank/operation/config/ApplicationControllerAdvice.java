package com.pismo.api.bank.operation.config;

import com.pismo.api.bank.operation.exception.EntityNotFoundException;
import com.pismo.api.bank.operation.exception.ExistsEntityException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            errors.add(globalError.getObjectName() + ": " + globalError.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ExistsEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleExistsEntityException(ExistsEntityException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Entity already exists", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Entity not found", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse("Validation failed" , e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
