package com.company.httpfacade;

import com.company.httpfactory.HttpFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class HttpJsonFacade<T> implements HttpFacade<T> {
    private final HttpFactory httpFactory;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public T get(String uri, Class<T> responseClass) {
        HttpRequest httpRequest = httpFactory.getRequestWithNoToken(uri);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T post(String uri, Object body, Class<T> responseClass) {

        HttpRequest httpRequest = httpFactory.postRequestWithNoToken(uri, body);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T getAuthorized(String uri, Class<T> responseClass) {
        try {
            HttpRequest httpRequest = httpFactory.getRequestWithToken(uri);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T postAuthorized(String uri, Object body, Class<T> responseClass) {
        HttpRequest httpRequest = httpFactory.postRequestWithNoToken(uri, body);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }
}
