package com.company.config;

import com.company.annotation.MyConfigAnnotation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppConfig {
    @MyConfigAnnotation("app.service.workmode")
    private String workmode;
    @MyConfigAnnotation("api.base-uri")
    private String baseURL;
    @MyConfigAnnotation("file.path")
    private String filePath;

}
