package com.company.util;

import com.company.httpfactory.HttpFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class HttpJsonFacade {
    private final HttpFactory httpFactory;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public <T> T get(String uri, Class<T> responseClass) {
        HttpRequest httpRequest = httpFactory.getRequestWithNoToken(uri);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T post(String uri, Object body, Class<T> responseClass) {

        HttpRequest httpRequest = httpFactory.postRequestWithNoToken(uri, body);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getAuthorized(String uri, Class<T> responseClass) {
        try {
            HttpRequest httpRequest = httpFactory.getRequestWithToken(uri);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T postAuthorized(String uri, Object body, Class<T> responseClass) {
        HttpRequest httpRequest = httpFactory.postRequestWithToken(uri, body);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }
}
