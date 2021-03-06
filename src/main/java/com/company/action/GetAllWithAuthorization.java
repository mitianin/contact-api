package com.company.action;

import com.company.service.UserService;
import com.company.util.TokenData;
import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
public class GetAllWithAuthorization implements Action {
    private final UserService userService;


    @Override
    public String doIt() {
        String token = userService.getToken();

        if (token == null) return "No token to log in";
        return userService.getAllWithLogin(token).toString();
    }
    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "Get all users with authorization".toUpperCase(Locale.ROOT);
    }
}
