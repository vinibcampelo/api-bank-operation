package com.pismo.api.bank.operation.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
