package com.company.httpfactory;

import java.net.http.HttpRequest;

public interface HttpFactory {
    HttpRequest getRequestWithToken(String url);

    HttpRequest getRequestWithNoToken(String url);

    HttpRequest postRequestWithNoToken(String url, Object obj);

    HttpRequest postRequestWithToken(String url, Object obj);
}
