package br.com.caelum.carangobom.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgreConfiguration {

    @Bean
    public DataSource getDatabaseConnection() {
        String databaseUrl = System.getenv().get("DATABASE_URI");
        String databaseUser = System.getenv().get("DATABASE_USER");
        String databasePassword = System.getenv().get("DATABASE_PASSWORD");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUser);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaximumPoolSize(20);

        return new HikariDataSource(hikariConfig);
    }
}
