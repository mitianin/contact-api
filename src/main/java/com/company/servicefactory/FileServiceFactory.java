package com.company.servicefactory;

import com.company.util.HttpJsonFacade;
import com.company.httpfactory.HttpFactory;
import com.company.service.ContactService;
import com.company.service.ContactServiceFile;
import com.company.service.UserService;
import com.company.service.UserServiceApi;
import com.company.util.TokenData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class FileServiceFactory implements ServiceFactory {
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final String filePath;
    private final HttpJsonFacade httpJsonFacade;
    private final TokenData tokenData;

    @Override
    public ContactService createContactService() {
        return new ContactServiceFile(objectMapper, filePath, tokenData);
    }

    @Override
    public UserService createUserService() {
        return new UserServiceApi(objectMapper, baseUri, httpJsonFacade, tokenData);
    }
}
