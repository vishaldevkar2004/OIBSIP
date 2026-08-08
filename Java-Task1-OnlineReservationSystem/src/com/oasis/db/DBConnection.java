package com.oasis.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("db.properties")) {

            properties.load(fis);

            URL = properties.getProperty("db.url");
            USERNAME = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Unable to load database configuration.", e);
        }
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException("Database Connection Failed.", e);
        }
    }
}