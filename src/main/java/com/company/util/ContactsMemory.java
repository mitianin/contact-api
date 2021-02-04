package com.company.util;

import com.company.dto.FindContact;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ContactsMemory {
    public static int id = 0;
    public static List<FindContact> memoryContacts = new ArrayList<>();
}
