import com.fasterxml.jackson.databind.ObjectMapper;
import action.*;
import service.UserService;

import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        UserService userService = new UserService(httpClient, objectMapper);

        Scanner s = new Scanner(System.in);
//
        Menu menu = new Menu();

        menu.addAction(new Login(userService, s));
        menu.addAction(new Registration(userService, s));
        menu.addAction(new GetAll(userService));
        menu.addAction(new GetAllWithAuthorization(userService));
        menu.addAction(new FindByName(userService, s));
        menu.addAction(new FindByValue(userService, s));
        menu.addAction(new FindAllContact(userService));
        menu.addAction(new Add(userService, s));
        menu.addAction(new Close());



            while (true) {
                if (userService.getToken() != null) break;
                menu.showAction(menu.getActionWithNoToken());
                int choice = Integer.parseInt(s.nextLine());
                if (menu.getActionWithNoToken().get(choice - 1) instanceof Close) break;
                menu.execute(choice, menu.getActionWithNoToken());
            }

            while (true) {
                menu.showAction(menu.getActionWithToken());
                int choice = Integer.parseInt(s.nextLine());
                if (menu.getActionWithToken().get(choice - 1) instanceof Close) return;
                menu.execute(choice, menu.getActionWithToken());
            }









    }
}
