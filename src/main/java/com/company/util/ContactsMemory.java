package com.company.util;

import com.company.dto.FindContact;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContactsMemory {
    public int id = 0;
    public List<FindContact> memoryContacts = new ArrayList<>();
}
