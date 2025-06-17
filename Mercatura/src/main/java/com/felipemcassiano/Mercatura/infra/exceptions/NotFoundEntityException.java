package com.felipemcassiano.Mercatura.infra.exceptions;

public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException(String message) {
        super(message);
    }
}
