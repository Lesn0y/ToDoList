package com.lesnoy.todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private final String url = "jdbc:postgresql://localhost:5432/todo";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver not found");
            throw new RuntimeException(e);
        }
    }

    public List<Task> getAll() {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String sqlQuery = "SELECT * FROM tasks";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("task"),
                        resultSet.getTimestamp("deadline"),
                        resultSet.getBoolean("is_done")
                ));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> save(Task task) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String sqlQuery = String.format("INSERT INTO tasks(task, deadline) VALUES ('%s', '%s');", task.getTask(), task.getDeadline());
            int i = statement.executeUpdate(sqlQuery);
            if (i == 1) {
                List<Task> tasks = new ArrayList<>();
                sqlQuery = "SELECT * FROM tasks";
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    tasks.add(new Task(
                            resultSet.getInt("id"),
                            resultSet.getString("task"),
                            resultSet.getTimestamp("deadline"),
                            resultSet.getBoolean("is_done")
                    ));
                }
                return tasks;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String sqlQuery = String.format("DELETE FROM tasks WHERE id=%d;", id);
            return statement.executeUpdate(sqlQuery) == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateDone(int id) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String sqlQuery = String.format("UPDATE tasks SET is_done = true WHERE id=%d;", id);
            return statement.executeUpdate(sqlQuery) == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
