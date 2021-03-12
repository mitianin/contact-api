package com.company.servicefactory;

import com.company.service.ContactService;
import com.company.service.ContactServiceDb;
import com.company.service.UserService;
import com.company.service.UserServiceDb;
import com.company.util.db.CurrentUserData;
import com.company.util.db.MyDataBase;
import com.company.util.TokenData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataBaseServiceFactory implements ServiceFactory {

    private final MyDataBase mdb;
    private final CurrentUserData currentUserData;
    private final TokenData tokenData;

    @Override
    public ContactService createContactService() {
        return new ContactServiceDb(mdb, currentUserData);
    }

    @Override
    public UserService createUserService() {
        return new UserServiceDb(mdb, currentUserData, tokenData);
    }
}
