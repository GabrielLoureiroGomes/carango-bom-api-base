package br.com.caelum.carangobom.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Data
public class PostgreConfiguration {

    public static DataSource getDatabaseConnection() {
        String databaseUrl = System.getenv().get("DATABASE_URI");
        String databaseUser = System.getenv().get("DATABASE_USER");
        String databasePassword = System.getenv().get("DATABASE_PASSWORD");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUser);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(hikariConfig);
    }
}
