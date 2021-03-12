package com.company.service;

import com.company.dto.LoginResponse;
import com.company.dto.RegisterResponse;
import com.company.dto.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAll();

    LoginResponse login(String login, String password);

    List<User> getAllWithLogin(String token);

    RegisterResponse register(String login, String password, String dateBorn);

    default String getToken() {
        return null;
    }
}
