package ua.nure.cpp.sivenko.practice6.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public final class ConnectionFactory {
    private static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/pawnshop";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "strong_password";

    public static Connection createMySQLConnection() {
        try {
            return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
