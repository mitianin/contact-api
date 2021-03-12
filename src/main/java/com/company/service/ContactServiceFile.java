package com.company.service;

import com.company.dto.AddResponse;
import com.company.dto.FindContact;
import com.company.util.TokenData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class ContactServiceFile implements ContactService {
    private final ObjectMapper objectMapper;
    private final String filePath;
    private final TokenData tokenData;


    private List<FindContact> contacts = new ArrayList<>();

    @Override
    public List<FindContact> findAllContacts(String token) {
        contacts.clear();

        if (!new File(filePath).exists()) {
            return contacts;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.ready()) {
                contacts.add(objectMapper.readValue(br.readLine(), FindContact.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public List<FindContact> findByName(String name, String token) {
        return contacts.stream().
                filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<FindContact> findByValue(String value, String token) {
        return contacts.stream().
                filter(x -> x.getValue().equals(value))
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(String type, String value, String name, String token) {
        AddResponse addResponse = new AddResponse();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {

            int contactId = setId() + 1;

            String contact = objectMapper.
                    writeValueAsString(new FindContact(name, value, type, Integer.toString(contactId)));
            bw.write(contact + "\n");
            addResponse.setStatus("ok");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        addResponse.setStatus("error");
        return false;
    }

    private int setId() {
        return findAllContacts(tokenData.getToken()).
                stream().
                mapToInt(x -> Integer.parseInt(x.getId())).
                max().
                orElse(0);
    }
}
