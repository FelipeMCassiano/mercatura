package com.felipemcassiano.Mercatura.infra.exceptions;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("Not enough stock");
    }
}
