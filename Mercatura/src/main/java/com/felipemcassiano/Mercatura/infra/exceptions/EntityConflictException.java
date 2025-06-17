package com.felipemcassiano.Mercatura.infra.exceptions;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}
