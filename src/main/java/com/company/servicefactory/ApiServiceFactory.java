package com.company.servicefactory;

import com.company.service.ContactService;
import com.company.service.ContactServiceApi;
import com.company.service.UserService;
import com.company.service.UserServiceApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class ApiServiceFactory implements ServiceFactory{
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    //private final UserService userService;

    @Override
    public ContactService createContactService() {
        return new ContactServiceApi(httpClient, objectMapper, baseUri);
    }

    @Override
    public UserService createUserService() {
        return new UserServiceApi(httpClient, objectMapper, baseUri);
    }
}
