package com.company.servicefactory;

import com.company.service.ContactService;
import com.company.service.ContactServiceDb;
import com.company.service.UserService;
import com.company.service.UserServiceDb;
import com.company.util.MyDataBase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataBaseServiceFactory implements ServiceFactory {

    private final MyDataBase mdb;

    @Override
    public ContactService createContactService() {
        return new ContactServiceDb(mdb);
    }

    @Override
    public UserService createUserService() {
        return new UserServiceDb(mdb);
    }
}
