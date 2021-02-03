package com.company.action;

public interface Action {
    String doIt();
    default boolean needToken(){
        return false;
    }
}
