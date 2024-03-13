package com.escihu.apiescihuvirtual.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String emailAlreadyTaken) {
        super(emailAlreadyTaken);
    }
}
