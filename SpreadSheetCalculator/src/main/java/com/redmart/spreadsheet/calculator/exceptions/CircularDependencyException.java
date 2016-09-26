package com.redmart.spreadsheet.calculator.exceptions;

public class CircularDependencyException extends Exception {

    public CircularDependencyException() {
        super();
    }

    public CircularDependencyException(String message) {
        super(message);
    }

    public CircularDependencyException(Throwable t) {
        super(t);
    }
}
