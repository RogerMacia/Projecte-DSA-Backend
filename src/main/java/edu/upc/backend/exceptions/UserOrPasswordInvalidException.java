package edu.upc.backend.exceptions;

public class UserOrPasswordInvalidException extends RuntimeException {

    public UserOrPasswordInvalidException(String message) {
        super(message);
    }
}
