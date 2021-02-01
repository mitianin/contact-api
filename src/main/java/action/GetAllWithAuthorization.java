package action;

import lombok.AllArgsConstructor;
import service.UserService;

import java.io.File;
import java.util.Locale;

@AllArgsConstructor
public class GetAllWithAuthorization implements ActionWithToken{
    private final UserService userService;


    @Override
    public String doIt() {
        String token = userService.getToken();

        if (!new File("token.txt").exists() || token == null) return "No token to log in";
        return userService.getAllWithLogin(token).toString();
    }

    @Override
    public String toString() {
        return "Get all users with authorization".toUpperCase(Locale.ROOT);
    }
}
