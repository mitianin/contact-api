package com.company.service;

import com.company.dto.LoginResponse;
import com.company.dto.RegisterResponse;
import com.company.dto.User;
import com.company.exceptions.InvalidLoginDataException;
import com.company.exceptions.UserAlreadyExistsExceptions;
import com.company.util.db.CurrentUserData;
import com.company.util.db.MyDataBase;
import com.company.util.TokenData;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class UserServiceDb implements UserService {
    private final MyDataBase mdb;
    private final CurrentUserData currentUserData;
    private final TokenData tokenData;

    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getAll() {
        users.clear();

        try (Connection connection = mdb.getDs().getConnection()) {

            PreparedStatement ps = connection.prepareStatement("select login, date_born from users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setDateBorn(rs.getString("date_born"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public LoginResponse login(String login, String password) {
        if (!isInTable(login)) throw new InvalidLoginDataException();

        LoginResponse loginResponse = new LoginResponse();

        try (Connection connection = mdb.getDs().getConnection()) {

            PreparedStatement ps =
                    connection.prepareStatement("select login, password from users_log where login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            rs.next();
            if (!rs.getString("login").equals(login) ||
                    !rs.getString("password").equals(password))
                throw new InvalidLoginDataException();

            ps = connection.prepareStatement("select id from users where login = ?");
            ps.setString(1, login);
            rs = ps.executeQuery();
            rs.next();

            currentUserData.setId(rs.getInt("id"));

            loginResponse.setStatus("ok");
            tokenData.setToken("");
            tokenData.setTokenDate(new Date());

            return loginResponse;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        loginResponse.setError("error");
        return loginResponse;
    }

    @Override
    public List<User> getAllWithLogin(String token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisterResponse register(String login, String password, String dateBorn) {

        if (isInTable(login)) throw new UserAlreadyExistsExceptions();
        RegisterResponse registerResponse = new RegisterResponse();

        try (Connection connection = mdb.getDs().getConnection()) {

            PreparedStatement ps = connection.prepareStatement("" +
                    "insert into users (login, date_born) " +
                    "values (?, ?);");
            ps.setString(1, login);
            ps.setTimestamp(2, Timestamp.valueOf(dateBorn + " 00:00:00.000000"));
            ps.execute();


            ps = connection.prepareStatement("insert into users_log (login, password) values (?,?);");
            ps.setString(1, login);
            ps.setString(2, password);
            ps.execute();

            registerResponse.setStatus("ok");

            return registerResponse;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        registerResponse.setError("error");
        return registerResponse;


    }

    private boolean isInTable(String pkData) {
        try (Connection connection = mdb.getDs().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select login from users where login = ?");
            ps.setString(1, pkData);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}

