package com.company.service;

import com.company.util.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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

    @Override
    public List<User> getAll() {
        UserResponse userResponse = null;
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/users"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (userResponse != null) return userResponse.getUsers();
        else return null;
    }

    @Override
    public LoginResponse login(String login, String password) {

        try {
            String logData = objectMapper.writeValueAsString(new LoginRequest(login, password));

            HttpRequest httpRequest =
                    HttpRequest.newBuilder().
                            uri(new URI(baseUri + "/login"))
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(logData))
                            .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            LoginResponse loginResponse = objectMapper.readValue(response.body(), LoginResponse.class);

            if (loginResponse.getToken() != null) {
                Token.token = loginResponse.getToken();
                Token.tokenDate = new Date();
            }
            return loginResponse;


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllWithLogin(String token) {
        UserResponse userResponse = null;
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/users2"))
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
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
            HttpRequest httpRequest =
                    HttpRequest.newBuilder().
                            uri(new URI(baseUri + "/register"))
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(regData))
                            .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            registerResponse = objectMapper.readValue(response.body(), RegisterResponse.class);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return registerResponse;
    }
}
