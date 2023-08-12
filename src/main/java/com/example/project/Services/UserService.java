package com.example.project.Services;

import com.example.project.Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    private Connection C;

    public UserService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            C = Db.getDataSource().getConnection();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public User getUser(String username, String password) {
        User U = null;
        try {
            PreparedStatement ps = this.C.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String pwd = resultSet.getString("password");
                String role = resultSet.getString("role");

                U= new User(id,name,pwd,role);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        return U;
    }

}
