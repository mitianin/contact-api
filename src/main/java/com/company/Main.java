package com.company;

import com.company.action.*;
import com.company.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        InputStream in =
                Main.class.getClassLoader().getResourceAsStream("jar-prop.properties");
        Properties prop = System.getProperties();

        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        UserService userService = null;
        Service contactService = null;

        if (prop.getProperty("contactbook.profile") != null) {
            try {
                prop.load(in);
                userService = new UserService(httpClient,
                        objectMapper, prop.getProperty("base-url"));

                switch (prop.getProperty("contactbook.profile")) {
                    case "prod":
                        contactService = new ContactServiceApi(httpClient, objectMapper, userService);
                        break;
                    case "dev":
                        contactService = new ContactServiceFile(userService,
                                objectMapper, prop.getProperty("file-path"));
                        break;
                    case "memory":
                        contactService = new ContactServiceMemory(userService);
                        break;
                    default:
                        System.out.println("WRONG VM OPTION");
                        return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("NO CONTACTBOOK.PROFILE OPTION");
            return;
        }

        Scanner s = new Scanner(System.in);
        Menu menu = new Menu();

        menu.addAction(new Login(userService, s));
        menu.addAction(new Registration(userService, s));
        menu.addAction(new GetAll(userService));
        menu.addAction(new GetAllWithAuthorization(userService));
        menu.addAction(new FindByName(contactService, s));
        menu.addAction(new FindByValue(contactService, s));
        menu.addAction(new FindAllContact(contactService));
        menu.addAction(new Add(contactService, s));
        menu.addAction(new Close());


        while (userService.getToken() == null) {
            menu.showAction(menu.getActionWithNoToken());
            int choice = Integer.parseInt(s.nextLine());
            if (menu.getActionWithNoToken().get(choice - 1) instanceof Close) return;
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
