import com.fasterxml.jackson.databind.ObjectMapper;
import action.*;
import service.UserService;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        UserService userService = new UserService(httpClient, objectMapper, "token.txt");

        Scanner s = new Scanner(System.in);

        Menu menu = new Menu();

        menu.addToActionWithNoToken(new Login(userService, s));
        menu.addToActionWithNoToken(new Registration(userService, s));
        menu.addToActionWithNoToken(new GetAll(userService));
        menu.addToActionWithNoToken (new Close());


        menu.addToMenuActionsWithToken(new GetAllWithAuthorization(userService));
        menu.addToMenuActionsWithToken(new FindByName(userService, s));
        menu.addToMenuActionsWithToken(new FindByValue(userService, s));
        menu.addToMenuActionsWithToken(new FindAllContact(userService));
        menu.addToMenuActionsWithToken(new Add(userService, s));
        menu.addToMenuActionsWithToken(new Close());



        List<ActionWithNoToken> actionWithNoToken = menu.getActionWithNoToken();
        List<ActionWithToken> actionWithToken = menu.getActionWithToken();


            while (true) {
                if (userService.getToken() != null) break;
                menu.showAction(actionWithNoToken);
                int choice = Integer.parseInt(s.nextLine());
                if (menu.getActionWithNoToken().get(choice - 1) instanceof Close) break;
                menu.execute1(choice, actionWithNoToken);
            }

            while (true) {
                menu.showAction(actionWithToken);
                int choice = Integer.parseInt(s.nextLine());
                if (menu.getActionWithToken().get(choice - 1) instanceof Close) break;
                menu.execute2(choice, actionWithToken);
            }








    }
}
