package com.company.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse implements Response {
    private String status;
    private String error;
    private String token;
}
