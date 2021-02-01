package action;

import lombok.AllArgsConstructor;
import service.UserService;

import java.util.Scanner;

@AllArgsConstructor

public class Registration implements ActionWithNoToken {

    private final UserService userService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER LOGIN, PASSWORD AND DATE_BIRTH");


        return userService.register(s.nextLine(), s.nextLine(), s.nextLine()).toString();
    }

    @Override
    public String toString() {
        return "REGISTER";
    }
}
