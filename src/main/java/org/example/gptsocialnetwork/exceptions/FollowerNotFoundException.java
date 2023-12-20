package org.example.gptsocialnetwork.exceptions;

public class FollowerNotFoundException extends RuntimeException {
    public FollowerNotFoundException(String message) {
        super(message);
    }
}
