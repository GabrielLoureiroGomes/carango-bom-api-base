package br.com.caelum.carangobom.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreConfiguration {

    Connection conn = null;
    String databaseUrl = System.getenv().get("DATABASE_URL");

    public Connection getDatabaseConnection() throws SQLException {
        conn = DriverManager.getConnection(databaseUrl);
        return conn;
    }
}
