package com.felipemcassiano.Mercatura.infra.exceptions;

public class InternalException extends RuntimeException {
    public InternalException(String message) {
        super(message);
    }
}
