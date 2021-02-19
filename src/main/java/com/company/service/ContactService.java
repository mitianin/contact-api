package com.company.service;

import com.company.dto.AddResponse;
import com.company.dto.FindContact;

import java.util.List;

public interface ContactService {

    List<FindContact> findAllContacts(String token);
    List<FindContact> findByName(String name, String token);
    List<FindContact> findByValue(String value, String token);
    boolean add(String type, String value, String name, String token);
}
