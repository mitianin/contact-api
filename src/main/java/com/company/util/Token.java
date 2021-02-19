package com.company.util;

import lombok.Data;

import java.util.Date;

@Data
public class Token {
    public static String token;
    public static Date tokenDate;

    public static String getToken(){
        if (tokenDate == null || new Date().getTime() - tokenDate.getTime() > 600000) return null;
        return token;
    }

}
