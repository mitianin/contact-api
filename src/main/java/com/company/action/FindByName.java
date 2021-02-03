package com.company.action;

import lombok.Data;
import com.company.service.ContactService;

import java.util.Scanner;

@Data
public class FindByName implements Action {
    private final ContactService contactService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER NAME TO FIND");
        return contactService.findByName(s.nextLine(), contactService.getToken()).toString();
    }

    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "FIND CONTACT BY NAME";
    }
}
