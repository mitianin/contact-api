package com.company.action;

import com.company.service.Service;
import lombok.Data;

import java.util.Scanner;

@Data
public class FindByValue implements Action {
    private final Service service;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER VALUE TO FIND");
        return service.findByValue(s.nextLine(), service.getToken()).toString();
    }
    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "FIND CONTACT BY VALUE";
    }
}
