package com.lesnoy.todo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Postgres Driver is not found");
            throw new RuntimeException(e);
        }
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/todo");
        config.setUsername("lesnoy");
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
