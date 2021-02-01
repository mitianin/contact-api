package action;

import lombok.AllArgsConstructor;
import service.UserService;

import java.util.Locale;

@AllArgsConstructor
public class GetAll implements ActionWithNoToken {
    private final UserService userService;

    public String doIt(){
        return userService.getAll().toString();
    }

    @Override
    public String toString() {
        return "Get all users without authorization".toUpperCase(Locale.ROOT);
    }
}
