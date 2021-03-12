package com.company.action;

import com.company.service.ContactService;
import com.company.util.TokenData;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class Add implements Action {
    private final ContactService contactService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER TYPE, EMAIL AND NAME TO ADD");

        String type = s.nextLine();
        String value = s.nextLine();
        String name = s.nextLine();

        return String.valueOf(contactService.add(type, value, name, contactService.getToken()));
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
