package com.company.exeptions;

public class NoConfigFileException extends Exception {

    @Override
    public String toString() {
        return "config file doesn't exit! default URL and MemoryService will be used";
    }
}
