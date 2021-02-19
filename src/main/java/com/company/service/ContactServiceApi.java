package com.company.service;

import com.company.dto.AddRequest;
import com.company.dto.AddResponse;
import com.company.httpfactory.HttpFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.FindContact;
import com.company.dto.FindResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ContactServiceApi implements ContactService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private HttpFactory httpFactory;

    @Override
    public List<FindContact> findAllContacts(String token) {
        try {
            HttpRequest httpRequest = httpFactory.getRequestWithToken(baseUrl + "/contacts");

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);

            return findResponse.getContacts();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FindContact> findByName(String name, String token) {

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact(name, "", "", ""));

            HttpRequest httpRequest = httpFactory.postRequestWithToken(baseUrl + "/contacts/find", findData);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);


            return findResponse.getContacts()
                    .stream()
                    .filter((x) -> x.getName().equals(name))
                    .collect(Collectors.toList());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FindContact> findByValue(String value, String token) {

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact("", value, "", ""));

            HttpRequest httpRequest = httpFactory.postRequestWithToken(baseUrl + "/contacts/find", findData);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);

            return findResponse.getContacts().
                    stream()
                    .filter((x) -> x.getValue().equals(value))
                    .collect(Collectors.toList());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(String type, String value, String name, String token) {
        AddResponse addResponse = null;
        try {
            String addData = objectMapper.writeValueAsString(new AddRequest(type, value, name));
            HttpRequest httpRequest = httpFactory.postRequestWithToken(baseUrl + "/contacts/add", addData);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            addResponse = objectMapper.readValue(response.body(), AddResponse.class);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
