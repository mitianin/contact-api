package com.company.action;

import com.company.service.Service;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class Add implements Action {
    private final Service service;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER TYPE, EMAIL AND NAME TO ADD");

        String type = s.nextLine();
        String value = s.nextLine();
        String name = s.nextLine();

        return String.valueOf(service.add(type, value, name, service.getToken()));
    }
    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "ADD CONTACT";
    }
}
