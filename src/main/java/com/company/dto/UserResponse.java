package com.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse implements Response {
    private String status;
    private List<User> users;
    private String error;
}
