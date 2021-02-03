package com.company.action;

public class Close implements Action {
    @Override
    public String doIt() {
        return "Close com.company.action";
    }

    @Override
    public String toString() {
        return "CLOSE";
    }
}
