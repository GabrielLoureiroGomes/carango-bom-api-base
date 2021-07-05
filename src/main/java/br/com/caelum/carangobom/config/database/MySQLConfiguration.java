package br.com.caelum.carangobom.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConfiguration {

    String connectionUrl = "jdbc:mysql://localhost:3306/carangobom?serverTimezone=UTC";
    Connection conn = null;

    public Connection getDatabaseConnection() {
        try {
            conn = DriverManager.getConnection(connectionUrl, "user", "gj07092018$");
        } catch (SQLException e) {
            e.getCause();
        }
        return conn;
    }
}
