package com.epam.training.restapp.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }
}
