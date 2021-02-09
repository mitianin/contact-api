package com.company.exceptions;

public class NoConfigFileException extends RuntimeException {

    @Override
    public String toString() {
        return "config file doesn't exit / no VM option!\ndefault URL and MemoryService will be used";
    }
}
