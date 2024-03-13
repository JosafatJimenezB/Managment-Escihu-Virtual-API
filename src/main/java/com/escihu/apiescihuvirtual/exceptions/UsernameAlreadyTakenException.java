package com.escihu.apiescihuvirtual.exceptions;

public class UsernameAlreadyTakenException  extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
