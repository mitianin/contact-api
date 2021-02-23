package com.company.servicefactory;

import com.company.dto.UserResponse;
import com.company.httpfacade.HttpJsonFacade;
import com.company.httpfactory.HttpFactory;
import com.company.service.ContactService;
import com.company.service.ContactServiceMemory;
import com.company.service.UserService;
import com.company.service.UserServiceApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class MemoryServiceFactory implements ServiceFactory {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpFactory httpFactory;

    @Override
    public ContactService createContactService() {
        return new ContactServiceMemory();
    }

    @Override
    public UserService createUserService() {
        return new UserServiceApi(objectMapper, baseUri, new HttpJsonFacade(httpFactory, objectMapper, httpClient));
    }
}
