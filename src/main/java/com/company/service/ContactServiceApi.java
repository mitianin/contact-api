package com.company.service;

import com.company.dto.AddRequest;
import com.company.dto.AddResponse;
import com.company.util.HttpJsonFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.FindContact;
import com.company.dto.FindResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ContactServiceApi implements ContactService {

    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private HttpJsonFacade httpJsonFacade;

    @Override
    public List<FindContact> findAllContacts(String token) {
        FindResponse findResponse = httpJsonFacade.getAuthorized(baseUrl + "/contacts", FindResponse.class);
        return findResponse.getContacts();
    }

    @Override
    public List<FindContact> findByName(String name, String token) {

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact(name, "", "", ""));

            FindResponse findResponse =
                    httpJsonFacade.postAuthorized(baseUrl + "/contacts/find", findData, FindResponse.class);


            return findResponse.getContacts()
                    .stream()
                    .filter((x) -> x.getName().equals(name))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FindContact> findByValue(String value, String token) {

        try {
            String findData =
                    objectMapper.writeValueAsString(new FindContact("", value, "", ""));

            FindResponse findResponse =
                    httpJsonFacade.postAuthorized(baseUrl + "/contacts/find", findData, FindResponse.class);

            return findResponse.getContacts().
                    stream()
                    .filter((x) -> x.getValue().equals(value))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(String type, String value, String name, String token) {
        AddResponse addResponse = null;
        try {
            String addData = objectMapper.writeValueAsString(new AddRequest(type, value, name));
            addResponse =
                    httpJsonFacade.postAuthorized(baseUrl + "/contacts/add", addData, AddResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return addResponse != null;
    }
}
