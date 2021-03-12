package com.company.util;

import lombok.Data;

import java.util.Date;

@Data
public class TokenData {
    private String token;
    private Date tokenDate;

    public String getToken() {
        if (tokenDate == null || new Date().getTime() - tokenDate.getTime() > 600000) return null;
        return token;
    }

}
