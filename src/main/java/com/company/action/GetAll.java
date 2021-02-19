package com.company.action;

import com.company.service.UserService;
import lombok.AllArgsConstructor;
import com.company.service.UserServiceApi;

import java.util.Locale;

@AllArgsConstructor
public class GetAll implements Action {
    private final UserService userService;

    public String doIt(){
        return userService.getAll().toString();
    }

    @Override
    public String toString() {
        return "Get all users without authorization".toUpperCase(Locale.ROOT);
    }
}
