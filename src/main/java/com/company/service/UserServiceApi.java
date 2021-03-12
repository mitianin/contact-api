package com.company.service;

import com.company.util.HttpJsonFacade;
import com.company.util.TokenData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Data
public class UserServiceApi implements UserService {
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpJsonFacade facade;
    private final TokenData tokenData;

    @Override
    public List<User> getAll() {

        UserResponse userResponse = facade.get(baseUri + "/users", UserResponse.class);

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public LoginResponse login(String login, String password) {

        try {
            String logData = objectMapper.writeValueAsString(new LoginRequest(login, password));
            LoginResponse loginResponse =
                    facade.post(baseUri + "/login", logData, LoginResponse.class);

            if (loginResponse.getToken() != null) {
                tokenData.setToken(loginResponse.getToken());
                tokenData.setTokenDate(new Date());
            }
            return loginResponse;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllWithLogin(String token) {
        UserResponse userResponse =
                facade.getAuthorized(baseUri + "/users2", UserResponse.class);
        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public RegisterResponse register(String login, String password, String dateBorn) {
        try {
            String regData = objectMapper.writeValueAsString(new RegisterRequest(login, password, dateBorn));
            return facade.postAuthorized(baseUri + "/register", regData, RegisterResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
