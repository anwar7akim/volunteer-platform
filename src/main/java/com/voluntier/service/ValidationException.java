package com.voluntier.service;

/** Thrown when a business rule rejects an action. The message is shown to the user. */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) { super(message); }
}
