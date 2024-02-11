package com.lesnoy.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private static final Logger log = LoggerFactory.getLogger(TaskDAO.class);
    private final PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilder();

    public List<Task> getAll() {
        try (Connection connection = connectionBuilder.getConnection();
             Statement statement = connection.createStatement()) {
            return findALL(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> save(Task task) {
        try (Connection connection = connectionBuilder.getConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery;
            if (task.getDeadline() == null) {
                sqlQuery = String.format("INSERT INTO tasks(task) VALUES ('%s');", task.getTask());
            } else {
                sqlQuery = String.format("INSERT INTO tasks(task, deadline) VALUES ('%s', '%s');", task.getTask(), task.getDeadline());
            }
            int i = statement.executeUpdate(sqlQuery);
            if (i == 1) {
                return findALL(statement);
            }
            return null;
        } catch (SQLException e) {
            log.error("Error during entry '" + task.toString() + "' into the database");
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(int id) {
        String sqlQuery = "DELETE FROM tasks WHERE id=?;";
        try (Connection connection = connectionBuilder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error("Error deleting an entry with id=" + id + " from the database");
            throw new RuntimeException(e);
        }
    }

    public boolean updateDone(int id) {
        String sqlQuery = "UPDATE tasks SET is_done = true WHERE id=?;";
        try (Connection connection = connectionBuilder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error("Error modifying an entry with id=" + id + " from the database");
            throw new RuntimeException(e);
        }
    }

    private List<Task> findALL(Statement statement) throws SQLException {
        String sqlQuery = "SELECT * FROM tasks ORDER BY is_done, deadline";
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
    }
}
