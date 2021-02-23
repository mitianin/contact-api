package com.company.service;

import com.company.httpfacade.HttpFacade;
import com.company.httpfactory.HttpFactory;
import com.company.util.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Data
public class UserServiceApi implements UserService {
    //private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    //private final HttpFactory httpFactory;
    private final HttpFacade facade;

    @Override
    public List<User> getAll() {

        UserResponse userResponse = (UserResponse) facade.get(baseUri + "/users", UserResponse.class);

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public LoginResponse login(String login, String password) {

        try {
            String logData = objectMapper.writeValueAsString(new LoginRequest(login, password));
            LoginResponse loginResponse =
                    (LoginResponse) facade.post(baseUri + "/login", logData, LoginResponse.class);

            if (loginResponse.getToken() != null) {
                Token.token = loginResponse.getToken();
                Token.tokenDate = new Date();
            }
            return loginResponse;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllWithLogin(String token) {
        UserResponse userResponse = (UserResponse) facade.getAuthorized(baseUri + "/users2", UserResponse.class);
        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public RegisterResponse register(String login, String password, String dateBorn) {
        try {
            String regData = objectMapper.writeValueAsString(new RegisterRequest(login, password, dateBorn));
            RegisterResponse registerResponse =
                    (RegisterResponse) facade.postAuthorized(baseUri + "/register", regData, RegisterResponse.class);
            return registerResponse;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
