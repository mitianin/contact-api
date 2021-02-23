package com.company.servicefactory;

import com.company.httpfacade.HttpJsonFacade;
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
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpFactory httpFactory;

    @Override
    public ContactService createContactService() {
        return new ContactServiceApi(httpClient, objectMapper, baseUri, httpFactory);
    }

    @Override
    public UserService createUserService() {
        return new UserServiceApi(objectMapper, baseUri, new HttpJsonFacade(httpFactory, objectMapper, httpClient));
    }
}
