package com.company.servicefactory;

import com.company.util.HttpJsonFacade;
import com.company.httpfactory.HttpFactory;
import com.company.service.ContactService;
import com.company.service.ContactServiceApi;
import com.company.service.UserService;
import com.company.service.UserServiceApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class ApiServiceFactory implements ServiceFactory {
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpJsonFacade httpJsonFacade;

    @Override
    public ContactService createContactService() {
        return new ContactServiceApi(objectMapper, baseUri, httpJsonFacade);
    }

    @Override
    public UserService createUserService() {
        return new UserServiceApi(objectMapper, baseUri, httpJsonFacade);
    }
}
