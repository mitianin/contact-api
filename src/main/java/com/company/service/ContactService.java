package com.company.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.FindContact;
import com.company.dto.FindResponse;
import lombok.Data;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContactService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    private String baseUri = "https://mag-contacts-api.herokuapp.com";

    public List<FindContact> findAllContacts(String token){

        try {

            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(new URI(baseUri + "/contacts"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);

            return findResponse.getContacts();

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FindContact> findByName(String name, String token){

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact(name, "", "", ""));

            HttpRequest httpRequest = createPostRequestWithToken("/contacts/find", findData, token);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);


            return findResponse.getContacts()
                    .stream()
                    .filter((x) -> x.getName().equals(name))
                    .collect(Collectors.toList());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FindContact> findByValue(String value, String token){

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact("", value, "", ""));

            HttpRequest httpRequest = createPostRequestWithToken("/contacts/find", findData, token);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FindResponse findResponse = objectMapper.readValue(response.body(), FindResponse.class);

            return  findResponse.getContacts().
                    stream()
                    .filter((x) -> x.getValue().equals(value))
                    .collect(Collectors.toList());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getToken(){
        return userService.getToken();
    }

    private HttpRequest createPostRequestWithToken(String path, String data, String token) throws URISyntaxException {
        return HttpRequest.newBuilder().
                uri(new URI(baseUri + path))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

    }
}
