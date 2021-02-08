package com.company.util;

import com.company.exeptions.NoConfigFileException;
import com.company.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Properties;

@RequiredArgsConstructor
public class CreateService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public Service createService(UserService userService) {
        Properties prop = loadProp();
        String mode = prop.getProperty("app.service.workmode");
        switch (mode) {
            case "api": return new ContactServiceApi(httpClient, objectMapper, userService);
            case "file": return new ContactServiceFile(userService, objectMapper, prop.getProperty("file.path"));
            default: return new ContactServiceMemory(userService);
        }
    }

    public String getBaseUrl() {
        return loadProp().getProperty("api.base-uri");
    }

    private File getConfigFile() {
        String mode = System.getProperty("contactbook.profile");
        File file = new File("app-" + mode + ".properties");

        if (!file.exists()) try {
            throw new NoConfigFileException();
        } catch (NoConfigFileException e) {
            e.printStackTrace();
            return new File("def.properties");
        }
        return file;
    }

    private Properties loadProp(){
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(getConfigFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
