package com.company.action;

import com.company.service.ContactService;
import com.company.util.TokenData;
import lombok.Data;

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
