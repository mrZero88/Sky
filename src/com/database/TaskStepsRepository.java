package com.database;

import com.base.BaseRepository;
import com.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskStepsRepository extends BaseRepository {

    public TaskStepsRepository() throws Exception {
        super();
    }

    public TaskStep insert(TaskStep taskStep) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into task_steps " +
                            "(description, " +
                            "order, " +
                            "task_id, " +
                            "is_done, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, taskStep.getDescription());
            preparedStatement.setInt(2, taskStep.getOrder());
            preparedStatement.setLong(3, taskStep.getTaskId());
            preparedStatement.setBoolean(4, taskStep.getIsDone());
            preparedStatement.setTimestamp(5, taskStep.getCreatedAt());
            preparedStatement.setTimestamp(6, taskStep.getUpdatedAt());
            preparedStatement.setLong(7, taskStep.getCreatedUserId());
            preparedStatement.setLong(8, taskStep.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from task_steps order by id desc limit 1");
                resultSet.next();
                taskStep.setId(resultSet.getInt("id"));
            }
            return taskStep;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public TaskStep getById(long id) throws Exception{
        try {
            connect = DriverManager.getConnection(CONN);
            TaskStep taskStep = new TaskStep();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from task_steps where id=" + id);
            resultSet.next();
            taskStep.setId(resultSet.getInt("id"));
            taskStep.setDescription(resultSet.getString("description"));
            taskStep.setOrder(resultSet.getInt("order"));
            taskStep.setTaskId(resultSet.getLong("task_id"));
            taskStep.setIsDone(resultSet.getBoolean("is_done"));
            taskStep.setCreatedAt(resultSet.getTimestamp("created_at"));
            taskStep.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            taskStep.setCreatedUserId(resultSet.getLong("created_user_id"));
            taskStep.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            taskStep.setActive(resultSet.getBoolean("active"));
            return taskStep;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<TaskStep> getByTaskId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<TaskStep> taskSteps = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from task_steps where active=true and task_id=" + id);
            while (resultSet.next()) {
                TaskStep taskStep = new TaskStep();
                taskStep.setId(resultSet.getInt("id"));
                taskStep.setDescription(resultSet.getString("description"));
                taskStep.setOrder(resultSet.getInt("order"));
                taskStep.setTaskId(resultSet.getLong("task_id"));
                taskStep.setIsDone(resultSet.getBoolean("is_done"));
                taskStep.setCreatedAt(resultSet.getTimestamp("created_at"));
                taskStep.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                taskStep.setCreatedUserId(resultSet.getLong("created_user_id"));
                taskStep.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                taskStep.setActive(resultSet.getBoolean("active"));
                taskSteps.add(taskStep);
            }
            return FXCollections.observableList(taskSteps);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<TaskStep> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<TaskStep> taskSteps = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from task_steps where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                TaskStep taskStep = new TaskStep();
                taskStep.setId(resultSet.getInt("id"));
                taskStep.setDescription(resultSet.getString("description"));
                taskStep.setOrder(resultSet.getInt("order"));
                taskStep.setTaskId(resultSet.getLong("task_id"));
                taskStep.setIsDone(resultSet.getBoolean("is_done"));
                taskStep.setCreatedAt(resultSet.getTimestamp("created_at"));
                taskStep.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                taskStep.setCreatedUserId(resultSet.getLong("created_user_id"));
                taskStep.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                taskStep.setActive(resultSet.getBoolean("active"));
                taskSteps.add(taskStep);
            }
            return FXCollections.observableList(taskSteps);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<TaskStep> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<TaskStep> taskSteps = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from task_steps where active=true");
            while (resultSet.next()) {
                TaskStep taskStep = new TaskStep();
                taskStep.setId(resultSet.getInt("id"));
                taskStep.setDescription(resultSet.getString("description"));
                taskStep.setOrder(resultSet.getInt("order"));
                taskStep.setTaskId(resultSet.getLong("task_id"));
                taskStep.setIsDone(resultSet.getBoolean("is_done"));
                taskStep.setCreatedAt(resultSet.getTimestamp("created_at"));
                taskStep.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                taskStep.setCreatedUserId(resultSet.getLong("created_user_id"));
                taskStep.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                taskStep.setActive(resultSet.getBoolean("active"));
                taskSteps.add(taskStep);
            }
            return FXCollections.observableList(taskSteps);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public TaskStep update(TaskStep taskStep) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            taskStep.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update task_steps set" +
                            " description = ?," +
                            " order = ?," +
                            " task_id = ?," +
                            " is_done = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, taskStep.getDescription());
            preparedStatement.setInt(2, taskStep.getOrder());
            preparedStatement.setLong(3, taskStep.getTaskId());
            preparedStatement.setBoolean(4, taskStep.getIsDone());
            preparedStatement.setTimestamp(5, taskStep.getUpdatedAt());
            preparedStatement.setLong(6, taskStep.getUpdatedUserId());
            preparedStatement.setLong(7, taskStep.getId());
            preparedStatement.executeUpdate();
            return taskStep;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public TaskStep delete(TaskStep taskStep) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            taskStep.setActive(false);
            preparedStatement = connect.prepareStatement("update task_steps set active = ? where id = ?");
            preparedStatement.setBoolean(1, taskStep.getActive());
            preparedStatement.setLong(2, taskStep.getId());
            preparedStatement.executeUpdate();
            return taskStep;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<TaskStep> taskSteps) throws Exception {
        if (taskSteps.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (TaskStep taskStep : taskSteps) {
            set.add("" + taskStep.getCreatedUserId());
            set.add("" + taskStep.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (TaskStep taskStep : taskSteps) {
            for (User user : users) {
                if (taskStep.getCreatedUserId() == user.getId())
                    taskStep.setCreatedUser(user);
                if (taskStep.getUpdatedUserId() == user.getId())
                    taskStep.setUpdatedUser(user);
            }
        }
    }

    public void loadTasks(ObservableList<TaskStep> taskSteps) throws Exception {
        if (taskSteps.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (TaskStep taskStep : taskSteps) {
            set.add("" + taskStep.getTaskId());
        }

        TasksRepository tr = new TasksRepository();
        ObservableList<Task> tasks = tr.getByIds(set);

        for (TaskStep taskStep : taskSteps) {
            for (Task task : tasks) {
                if (taskStep.getTaskId() == task.getId())
                    taskStep.setTask(task);
            }
        }
    }
}
