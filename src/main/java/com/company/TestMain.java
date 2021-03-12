package com.company;

import com.company.action.GetAll;
import com.company.action.GetAllWithAuthorization;
import com.company.action.*;
import com.company.action.Registration;
import com.company.config.AppConfig;
import com.company.config.PropertiesLoader;
import com.company.servicefactory.*;
import com.company.util.ContactsMemory;
import com.company.util.db.CurrentUserData;
import com.company.util.HttpJsonFacade;
import com.company.httpfactory.HttpFactory;
import com.company.httpfactory.HttpJsonRequestFactory;
import com.company.service.ContactService;
import com.company.service.UserService;
import com.company.util.db.MyDataBase;
import com.company.util.TokenData;
import com.company.util.db.MyDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.net.http.HttpClient;
import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        PropertiesLoader loader = new PropertiesLoader();
        AppConfig config = loader.getFileProps(AppConfig.class);

        TokenData tokenData = new TokenData();

        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpFactory httpFactory = new HttpJsonRequestFactory(tokenData);
        HttpJsonFacade httpJsonFacade = new HttpJsonFacade(httpFactory, objectMapper, httpClient);

        ServiceFactory factory;

        switch (config.getWorkmode()) {
            case "api": {
                factory = new ApiServiceFactory(objectMapper,
                        config.getBaseURL(),
                        httpJsonFacade,
                        tokenData);
                break;
            }
            case "file": {
                factory = new FileServiceFactory(objectMapper,
                        config.getBaseURL(),
                        config.getFilePath(),
                        httpJsonFacade,
                        tokenData);
                break;
            }
            case "database": {
                MyDataSource myDataSource =
                        new MyDataSource(config.getDsn(), config.getUser(), config.getPas());

                DataSource ds = myDataSource.createDataSource();
                MyDataBase mds = new MyDataBase(ds);
                mds.createTables();

                factory = new DataBaseServiceFactory(mds,
                        new CurrentUserData(),
                        tokenData);
                break;
            }
            default:
                factory = new MemoryServiceFactory(objectMapper,
                        config.getBaseURL(),
                        httpJsonFacade,
                        tokenData,
                        new ContactsMemory());
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


        while (tokenData.getToken() == null) {
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
