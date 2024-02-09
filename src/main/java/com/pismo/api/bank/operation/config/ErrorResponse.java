package com.pismo.api.bank.operation.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private final String message;
    private final List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message, String error) {
        this.message = message;
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

}