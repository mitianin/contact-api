package com.company;

import com.company.action.GetAll;
import com.company.action.GetAllWithAuthorization;
import com.company.action.*;
import com.company.action.Registration;
import com.company.config.AppConfig;
import com.company.config.PropertiesLoader;
import com.company.service.ContactService;
import com.company.service.UserService;
import com.company.servicefactory.ApiServiceFactory;
import com.company.servicefactory.FileServiceFactory;
import com.company.servicefactory.MemoryServiceFactory;
import com.company.servicefactory.ServiceFactory;
import com.company.util.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        PropertiesLoader loader = new PropertiesLoader();
        AppConfig config = loader.getFileProps(AppConfig.class);

        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();

        ServiceFactory factory;

        switch (config.getWorkmode()) {
            case "api": {
                factory = new ApiServiceFactory(httpClient, objectMapper, config.getBaseURL());
                break;
            }
            case "file": {
                factory = new FileServiceFactory(httpClient, objectMapper, config.getBaseURL(), config.getFilePath());
                break;
            }
            default: factory = new MemoryServiceFactory(httpClient, objectMapper, config.getBaseURL());
        }

        UserService us = factory.createUserService();
        ContactService cs = factory.createContactService();

        Scanner s = new Scanner(System.in);
        Menu menu = new Menu();

        menu.addAction(new Login(us, s));
        menu.addAction(new Registration(us, s));
        menu.addAction(new GetAll(us));
        menu.addAction(new GetAllWithAuthorization(us));
        menu.addAction(new FindByName(cs, s));
        menu.addAction(new FindByValue(cs, s));
        menu.addAction(new FindAllContact(cs));
        menu.addAction(new Add(cs, s));
        menu.addAction(new Close());


        while (Token.getToken() == null) {
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
