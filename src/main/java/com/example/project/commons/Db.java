package com.example.project.commons;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Db {
    static final String Url = "jdbc:mysql://localhost:3306/";
    static final String username = "root";
    static final String password = "root";

    private static HikariDataSource Hd;


    public static Connection getDataSource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            if(Hd==null) {
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(Url + "web");
                config.setUsername(username);
                config.setPassword(password);
                config.setMaximumPoolSize(1000000);
                Hd = new HikariDataSource(config);
            }
            return Hd.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
