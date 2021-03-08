package com.company.exceptions;

public class InvalidLoginDataException extends RuntimeException{
    @Override
    public String toString() {
        return "Wrong password or login";
    }
}
