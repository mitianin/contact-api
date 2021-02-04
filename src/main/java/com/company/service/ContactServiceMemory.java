package com.company.service;

import com.company.dto.AddResponse;
import com.company.dto.FindContact;
import com.company.util.ContactsMemory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class ContactServiceMemory implements Service{
    private final UserService userService;

    @Override
    public List<FindContact> findAllContacts(String token) {
        return ContactsMemory.memoryContacts;
    }

    @Override
    public List<FindContact> findByName(String name, String token) {
        return ContactsMemory.memoryContacts.stream()
                .filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<FindContact> findByValue(String value, String token) {
        return ContactsMemory.memoryContacts.stream()
                .filter(x -> x.getValue().equals(value))
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(String type, String value, String name, String token) {
        AddResponse addResponse = new AddResponse();
        addResponse.setStatus("ok");
        ContactsMemory.memoryContacts.add(new FindContact(name, value, type, Integer.toString(++ContactsMemory.id)));

        return true;
    }

    @Override
    public String getToken() {
        return userService.getToken();
    }
}
