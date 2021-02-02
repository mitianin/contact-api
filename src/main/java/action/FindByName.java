package action;

import lombok.Data;
import service.UserService;

import java.util.Scanner;

@Data
public class FindByName implements Action {
    private final UserService userService;
    private final Scanner s;

    @Override
    public String doIt() {
        System.out.println("ENTER NAME TO FIND");
        return userService.findByName(s.nextLine(), userService.getToken()).toString();
    }

    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "FIND CONTACT BY NAME";
    }
}
