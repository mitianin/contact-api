package action;

import lombok.Data;
import service.UserService;

@Data
public class FindAllContact implements Action {
    private final UserService userService;

    @Override
    public String doIt() {
        return userService.findAllContacts(userService.getToken()).toString();
    }

    @Override
    public boolean needToken() {
        return true;
    }

    @Override
    public String toString() {
        return "FIND ALL CONTACTS";
    }

}
