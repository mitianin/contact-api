package com.company.action;

import com.company.service.Service;
import lombok.Data;

@Data
public class FindAllContact implements Action {
    private final Service service;

    @Override
    public String doIt() {
        return service.findAllContacts(service.getToken()).toString();
    }

    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "FIND ALL CONTACTS";
    }

}
