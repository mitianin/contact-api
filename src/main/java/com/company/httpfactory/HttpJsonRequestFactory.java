package com.company.httpfactory;

import com.company.util.TokenData;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpRequest;

@RequiredArgsConstructor
public class HttpJsonRequestFactory implements HttpFactory {
    private final TokenData tokenData;

    @Override
    public HttpRequest getRequestWithToken(String url) {
        return HttpRequest.newBuilder().
                uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenData.getToken())
                .GET()
                .build();
    }

    @Override
    public HttpRequest getRequestWithNoToken(String url) {
        return HttpRequest.newBuilder().
                uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }

    @Override
    public HttpRequest postRequestWithNoToken(String url, Object obj) {
        return HttpRequest.newBuilder().
                uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString((String) obj))
                .build();
    }

    @Override
    public HttpRequest postRequestWithToken(String url, Object obj) {
        return HttpRequest.newBuilder().
                uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenData.getToken())
                .POST(HttpRequest.BodyPublishers.ofString((String) obj))
                .build();
    }
}
