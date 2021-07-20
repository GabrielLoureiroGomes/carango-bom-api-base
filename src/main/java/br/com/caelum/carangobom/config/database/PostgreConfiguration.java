package br.com.caelum.carangobom.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PostgreConfiguration {

    @Bean
    public static DataSource getDatabaseConnection() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String databaseUrl = System.getenv().get("DATABASE_URI");
        String databaseUser = System.getenv().get("DATABASE_USER");
        String databasePassword = System.getenv().get("DATABASE_PASSWORD");

        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUser);
        dataSource.setPassword(databasePassword);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }
}
