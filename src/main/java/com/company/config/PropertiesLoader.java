package com.company.config;

import com.company.annotation.MyConfigAnnotation;
import com.company.exceptions.NoConfigFileException;
import com.company.exceptions.NoDefaultConstructorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;


public class PropertiesLoader {

    private Object createObject(Class clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new NoDefaultConstructorException();
        }
    }

    public <T> T getFileProps(Class<T> clazz) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(getConfigFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object o = createObject(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(MyConfigAnnotation.class)) {
                MyConfigAnnotation an = field.getAnnotation(MyConfigAnnotation.class);
                String value = prop.getProperty(an.value());

                field.setAccessible(true);
                try {
                    field.set(o, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) o;
    }

    private File getConfigFile() {
        String mode = System.getProperty("contactbook.profile");
        File file = new File("app-" + mode + ".properties");

        if (!file.exists() || mode == null) try {
            throw new NoConfigFileException();
        } catch (NoConfigFileException e) {
            e.printStackTrace();
            return new File("def.properties");
        }
        return file;
    }


//    public void setFileProps(Object o) {
//        Properties prop = new Properties();
//        try {
//            prop.load(new FileInputStream(getConfigFile()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Class clazz = o.getClass();
//        for (Field field : clazz.getDeclaredFields()) {
//            if (field.isAnnotationPresent(MyConfigAnnotation.class)) {
//                MyConfigAnnotation an = field.getAnnotation(MyConfigAnnotation.class);
//                String value = prop.getProperty(an.value());
//
//                field.setAccessible(true);
//                try {
//                    field.set(o, value);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
