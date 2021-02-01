package action;

import lombok.AllArgsConstructor;
import service.UserService;

import java.util.Scanner;

@AllArgsConstructor
public class Add implements ActionWithToken{
    private final UserService userService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER TYPE, EMAIL AND NAME TO ADD");

        String type = s.nextLine();
        String value = s.nextLine();
        String name = s.nextLine();

        return userService.add(type, value, name, userService.getToken()).toString();
    }

    @Override
    public String toString() {
        return "ADD USER";
    }
}
