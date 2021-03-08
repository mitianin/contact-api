package com.company.exceptions;

public class UserAlreadyExistsExceptions extends RuntimeException {
    @Override
    public String toString() {
        return "User with such login exists in DB";
    }
}
