package com.company.service;

import com.company.httpfactory.HttpFactory;
import com.company.util.Token;
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
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final HttpFactory httpFactory;

    @Override
    public List<User> getAll() {
        UserResponse userResponse = null;
        try {

            HttpRequest httpRequest = httpFactory.getRequestWithNoToken(baseUri+"/users");
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public LoginResponse login(String login, String password) {

        try {
            String logData = objectMapper.writeValueAsString(new LoginRequest(login, password));
            HttpRequest httpRequest = httpFactory.postRequestWithNoToken(baseUri+"/login", logData);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            LoginResponse loginResponse = objectMapper.readValue(response.body(), LoginResponse.class);

            if (loginResponse.getToken() != null) {
                Token.token = loginResponse.getToken();
                Token.tokenDate = new Date();
            }
            return loginResponse;


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllWithLogin(String token) {
        UserResponse userResponse = null;
        try {
            HttpRequest httpRequest = httpFactory.getRequestWithToken(baseUri+"/users2");
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public RegisterResponse register(String login, String password, String dateBorn) {
        RegisterResponse registerResponse = null;
        try {
            String regData = objectMapper.writeValueAsString(new RegisterRequest(login, password, dateBorn));
            HttpRequest httpRequest = httpFactory.postRequestWithNoToken(baseUri+"/register", regData);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            registerResponse = objectMapper.readValue(response.body(), RegisterResponse.class);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return registerResponse;
    }
}
