package action;

import lombok.Data;
import service.UserService;

import java.util.Scanner;

@Data
public class FindByValue implements Action {
    private final UserService userService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER VALUE TO FIND");
        return userService.findByValue(s.nextLine(), userService.getToken()).toString();
    }
    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "FIND CONTACT BY VALUE";
    }
}
