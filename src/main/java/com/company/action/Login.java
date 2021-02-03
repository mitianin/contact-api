package com.company.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.company.service.UserService;

import java.util.Scanner;

@Data
@AllArgsConstructor
public class Login implements Action {
    private final UserService userService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER LOGIN AND PASSWORD TO LOGIN");

        return userService.login(s.nextLine(), s.nextLine()).getStatus();
    }

    @Override
    public String toString() {
        return "LOGIN IN";
    }
}
