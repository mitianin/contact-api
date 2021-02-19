package com.company.servicefactory;

import com.company.service.ContactService;
import com.company.service.UserService;

public interface ServiceFactory {
    ContactService createContactService();
    UserService createUserService();
}
