package com.company.httpfacade;

public interface HttpFacade<T> {
    T get(String uri, Class<T> responseClass);

    T post(String uri, Object body, Class<T> responseClass);

    T getAuthorized(String uri, Class<T> responseClass);

    T postAuthorized(String uri, Object body, Class<T> responseClass);
}
