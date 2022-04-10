package com.lift.shykhov.exception;

public class InvalidParameterException extends Exception {
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String message) {
        super("Invalid parameter: " + message);
    }
}
