package com.company.action;

import lombok.Data;
import com.company.service.ContactService;

@Data
public class FindAllContact implements Action {
    private final ContactService contactService;

    @Override
    public String doIt() {
        return contactService.findAllContacts(contactService.getToken()).toString();
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
