package com.company.exceptions;

public class NoDefaultConstructorException extends RuntimeException{
    @Override
    public String toString() {
        return "no default constructor";
    }
}
