package edu.upc.backend.exceptions;

public class InsufficientMoneyException extends Exception {
    public InsufficientMoneyException() {
        super("Insufficient money");
    }
}
