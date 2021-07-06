package br.com.caelum.carangobom.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreConfiguration {

    private PostgreConfiguration() {

    }

    public static Connection getDatabaseConnection() throws SQLException {

        String databaseUrl = System.getenv().get("DATABASE_URL");
        String databaseUser = System.getenv().get("DATABASE_USER");
        String databasePassword = System.getenv().get("DATABASE_PASSWORD");
        Connection conn = null;
        Properties props;

        props = new Properties();
        props.setProperty("user", databaseUser);
        props.setProperty("password", databasePassword);

        try {
            conn = DriverManager.getConnection(databaseUrl, props);
        } catch (SQLException e) {
            e.getCause();
        } finally {
            assert conn != null;
            conn.close();
        }
        return conn;
    }
}
