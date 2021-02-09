package com.company.util;

import com.company.config.AppConfig;
import com.company.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class CreateService2 {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AppConfig appConfig;

    public Service create(UserService userService) {
        String mode = appConfig.getWorkmode();
        switch (mode) {
            case "file": return new ContactServiceFile(userService, objectMapper, appConfig.getFilePath());
            case "api": return new ContactServiceApi(httpClient, objectMapper, userService);
            default: return new ContactServiceMemory(userService);
        }
    }


}
