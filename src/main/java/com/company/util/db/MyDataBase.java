package com.company.util.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;

@RequiredArgsConstructor
@Getter
public class MyDataBase {

    private final DataSource ds;

    public void createTables() {
        createUserTable();
        createContactTable();
        createUserLoggingDataTable();
    }

    private void createUserTable() {
        try {
            Connection connection = ds.getConnection();

            PreparedStatement ps = connection.prepareStatement("create table if not exists users " +
                    "(id serial primary key, " +
                    "login varchar not null unique, " +
                    "date_born timestamp);");

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createUserLoggingDataTable() {
        try {
            Connection connection = ds.getConnection();

            PreparedStatement ps = connection.prepareStatement("create table if not exists users_log " +
                    "(login varchar not null primary key, " +
                    "password varchar not null," +
                    "constraint pk_user_login foreign key (login) references users (login));");
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createContactTable() {
        try {
            Connection connection = ds.getConnection();

            PreparedStatement ps = connection.prepareStatement("create table if not exists contacts " +
                    "(id serial primary key, " +
                    "name varchar not null, " +
                    "value varchar not null unique, " +
                    "type varchar not null," +
                    "user_id int," +
                    "constraint pk_user_contacts foreign key (user_id) references users (id) on delete restrict);");
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
