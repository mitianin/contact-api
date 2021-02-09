package com.company;

import com.company.action.*;
import com.company.config.AppConfig;
import com.company.config.PropertiesLoader;
import com.company.service.*;
import com.company.util.CreateService2;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        PropertiesLoader loader = new PropertiesLoader();
        AppConfig appConfig = loader.getFileProps(AppConfig.class);
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        UserService userService = new UserService(httpClient, objectMapper, appConfig.getBaseURL());

        CreateService2 createService2 = new CreateService2(httpClient, objectMapper, appConfig);
        Service contactService = createService2.create(userService);

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
