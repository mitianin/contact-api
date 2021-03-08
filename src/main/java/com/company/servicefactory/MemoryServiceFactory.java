package com.company.servicefactory;

import com.company.util.HttpJsonFacade;
import com.company.service.ContactService;
import com.company.service.ContactServiceMemory;
import com.company.service.UserService;
import com.company.service.UserServiceApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemoryServiceFactory implements ServiceFactory {
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpJsonFacade httpJsonFacade;

    @Override
    public ContactService createContactService() {
        return new ContactServiceMemory();
    }

    @Override
    public UserService createUserService() {
        return new UserServiceApi(objectMapper, baseUri, httpJsonFacade);
    }
}
