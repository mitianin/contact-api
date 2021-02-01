package action;

import lombok.Data;
import service.UserService;

@Data
public class FindAllContact implements ActionWithToken{
    private final UserService userService;

    @Override
    public String doIt() {
        return userService.findAllContacts(userService.getToken()).toString();
    }

    @Override
    public String toString() {
        return "FIND ALL CONTACTS";
    }

}
