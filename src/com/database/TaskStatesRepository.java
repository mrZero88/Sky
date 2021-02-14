package com.database;

import com.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskStatesRepository extends BaseRepository {

    public TaskStatesRepository() throws Exception {
        super();
    }

    public TaskState insert(TaskState taskState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into task_states " +
                            "(title, " +
                            "state, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, taskState.getTitle());
            preparedStatement.setBinaryStream(2, taskState.getState());
            preparedStatement.setTimestamp(3, taskState.getCreatedAt());
            preparedStatement.setTimestamp(4, taskState.getUpdatedAt());
            preparedStatement.setLong(5, taskState.getCreatedUserId());
            preparedStatement.setLong(6, taskState.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from task_states order by id desc limit 1");
                resultSet.next();
                taskState.setId(resultSet.getInt("id"));
            }
            return taskState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public TaskState getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            TaskState taskState = new TaskState();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from task_states where id=" + id);
            resultSet.next();
            taskState.setId(resultSet.getInt("id"));
            taskState.setTitle(resultSet.getString("title"));
            taskState.setState(resultSet.getBinaryStream("state"));
            taskState.setCreatedAt(resultSet.getTimestamp("created_at"));
            taskState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            taskState.setCreatedUserId(resultSet.getLong("created_user_id"));
            taskState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            taskState.setActive(resultSet.getBoolean("active"));
            return taskState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<TaskState> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<TaskState> taskStates = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from task_states where active=true");
            while (resultSet.next()) {
                TaskState taskState = new TaskState();
                taskState.setId(resultSet.getInt("id"));
                taskState.setTitle(resultSet.getString("title"));
                taskState.setState(resultSet.getBinaryStream("state"));
                taskState.setCreatedAt(resultSet.getTimestamp("created_at"));
                taskState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                taskState.setCreatedUserId(resultSet.getLong("created_user_id"));
                taskState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                taskState.setActive(resultSet.getBoolean("active"));
                taskStates.add(taskState);
            }
            return FXCollections.observableList(taskStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public TaskState update(TaskState taskState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            taskState.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update task_states set title = ?," +
                            " state = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, taskState.getTitle());
            preparedStatement.setBinaryStream(2, taskState.getState());
            preparedStatement.setTimestamp(3, taskState.getUpdatedAt());
            preparedStatement.setLong(4, taskState.getUpdatedUserId());
            preparedStatement.setLong(5, taskState.getId());
            preparedStatement.executeUpdate();
            return taskState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public TaskState delete(TaskState taskState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            taskState.setActive(false);
            preparedStatement = connect.prepareStatement("update task_states set active = ? where id = ?");
            preparedStatement.setBoolean(1, taskState.getActive());
            preparedStatement.setLong(2, taskState.getId());
            preparedStatement.executeUpdate();
            return taskState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<TaskState> taskStates) throws Exception {
        if (taskStates.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (TaskState taskState : taskStates) {
            set.add("" + taskState.getCreatedUserId());
            set.add("" + taskState.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (TaskState taskState : taskStates) {
            for (User user : users) {
                if (taskState.getCreatedUserId() == user.getId())
                    taskState.setCreatedUser(user);
                if (taskState.getUpdatedUserId() == user.getId())
                    taskState.setUpdatedUser(user);
            }
        }
    }
}
