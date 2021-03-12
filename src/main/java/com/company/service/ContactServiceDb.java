package com.company.service;

import com.company.dto.FindContact;
import com.company.util.db.CurrentUserData;
import com.company.util.db.MyDataBase;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ContactServiceDb implements ContactService {
    private final MyDataBase mdb;
    private final CurrentUserData currentUserData;

    private final List<FindContact> contacts = new ArrayList<>();

    @Override
    public List<FindContact> findAllContacts(String token) {
        contacts.clear();

        try (Connection connection = mdb.getDs().getConnection()) {

            PreparedStatement ps = connection.prepareStatement("select id, name, value, type " +
                    "from contacts where user_id = ?");
            ps.setInt(1, currentUserData.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FindContact fc = new FindContact();
                fc.setId(String.valueOf(rs.getInt("id")));
                fc.setName(rs.getString("name"));
                fc.setValue(rs.getString("value"));
                fc.setType(rs.getString("type"));
                contacts.add(fc);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contacts;
    }

    @Override
    public List<FindContact> findByName(String name, String token) {
        contacts.clear();

        try (Connection connection = mdb.getDs().getConnection()) {

            PreparedStatement ps = connection.prepareStatement(
                    "select id, name, value, type " +
                            "from contacts where name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FindContact fc = new FindContact();
                fc.setName(rs.getString("name"));
                fc.setType(rs.getString("type"));
                fc.setId(String.valueOf(rs.getInt("id")));
                fc.setValue(rs.getString("value"));
                contacts.add(fc);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contacts;
    }

    @Override
    public List<FindContact> findByValue(String value, String token) {
        contacts.clear();

        try (Connection connection = mdb.getDs().getConnection()) {

            PreparedStatement ps = connection.prepareStatement(
                    "select id, name, value, type " +
                            "from contacts where value = ?");
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FindContact fc = new FindContact();
                fc.setValue(rs.getString("value"));
                fc.setType(rs.getString("type"));
                fc.setName(rs.getString("name"));
                fc.setId(String.valueOf(rs.getInt("id")));
                contacts.add(fc);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contacts;
    }

    @Override
    public boolean add(String type, String value, String name, String token) {
        try (Connection connection = mdb.getDs().getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement(
                            "insert into contacts (name, value, type, user_id) " +
                                    "values (?,?,?,?);");

            ps.setString(1, name);
            ps.setString(2, value);
            ps.setString(3, type);
            ps.setInt(4, currentUserData.getId());
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
