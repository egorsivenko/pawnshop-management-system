package ua.nure.cpp.sivenko.practice6.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties(prefix = "database")
@Getter
@Setter
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
