package com.javier.movier.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String s) {
        super(s);
    }
}
