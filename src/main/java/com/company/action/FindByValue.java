package com.company.action;

import com.company.service.ContactService;
import com.company.util.TokenData;
import lombok.Data;

import java.util.Scanner;

@Data
public class FindByValue implements Action {
    private final ContactService contactService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER VALUE TO FIND");
        return contactService.findByValue(s.nextLine(), contactService.getToken()).toString();
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
