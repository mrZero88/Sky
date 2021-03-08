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

public class TasksRepository extends BaseRepository {

    public TasksRepository() throws Exception {
        super();
    }

    public Task insert(Task task) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into tasks " +
                            "(title, " +
                            "description, " +
                            "start, " +
                            "end, " +
                            "issue_id, " +
                            "state_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setTimestamp(3, task.getStart());
            preparedStatement.setTimestamp(4, task.getEnd());
            preparedStatement.setLong(5, task.getIssueId());
            preparedStatement.setInt(6, task.getStateId());
            preparedStatement.setTimestamp(7, task.getCreatedAt());
            preparedStatement.setTimestamp(8, task.getUpdatedAt());
            preparedStatement.setLong(9, task.getCreatedUserId());
            preparedStatement.setLong(10, task.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from tasks order by id desc limit 1");
                resultSet.next();
                task.setId(resultSet.getInt("id"));
            }
            return task;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Task getById(long id) throws Exception{
        try {
            connect = DriverManager.getConnection(CONN);
            Task task = new Task();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from tasks where id=" + id);
            resultSet.next();
            task.setId(resultSet.getInt("id"));
            task.setTitle(resultSet.getString("title"));
            task.setDescription(resultSet.getString("description"));
            task.setStart(resultSet.getTimestamp("start"));
            task.setEnd(resultSet.getTimestamp("end"));
            task.setIssueId(resultSet.getLong("issue_id"));
            task.setStateId(resultSet.getInt("state_id"));
            task.setCreatedAt(resultSet.getTimestamp("created_at"));
            task.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            task.setCreatedUserId(resultSet.getLong("created_user_id"));
            task.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            task.setActive(resultSet.getBoolean("active"));
            return task;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Task> getByIssueId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Task> tasks = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from tasks where active=true and issue_id=" + id);
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setStart(resultSet.getTimestamp("start"));
                task.setEnd(resultSet.getTimestamp("end"));
                task.setIssueId(resultSet.getLong("issue_id"));
                task.setStateId(resultSet.getInt("state_id"));
                task.setCreatedAt(resultSet.getTimestamp("created_at"));
                task.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                task.setCreatedUserId(resultSet.getLong("created_user_id"));
                task.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                task.setActive(resultSet.getBoolean("active"));
                tasks.add(task);
            }
            return FXCollections.observableList(tasks);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Task> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Task> tasks = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from tasks where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setStart(resultSet.getTimestamp("start"));
                task.setEnd(resultSet.getTimestamp("end"));
                task.setIssueId(resultSet.getLong("issue_id"));
                task.setStateId(resultSet.getInt("state_id"));
                task.setCreatedAt(resultSet.getTimestamp("created_at"));
                task.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                task.setCreatedUserId(resultSet.getLong("created_user_id"));
                task.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                task.setActive(resultSet.getBoolean("active"));
                tasks.add(task);
            }
            return FXCollections.observableList(tasks);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Task> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Task> tasks = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from tasks where active=true");
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setStart(resultSet.getTimestamp("start"));
                task.setEnd(resultSet.getTimestamp("end"));
                task.setIssueId(resultSet.getLong("issue_id"));
                task.setStateId(resultSet.getInt("state_id"));
                task.setCreatedAt(resultSet.getTimestamp("created_at"));
                task.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                task.setCreatedUserId(resultSet.getLong("created_user_id"));
                task.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                task.setActive(resultSet.getBoolean("active"));
                tasks.add(task);
            }
            return FXCollections.observableList(tasks);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Task update(Task task) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            task.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update tasks set" +
                            " title = ?," +
                            " description = ?," +
                            " start = ?," +
                            " end = ?," +
                            " issue_id = ?," +
                            " state_id = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setTimestamp(3, task.getStart());
            preparedStatement.setTimestamp(4, task.getEnd());
            preparedStatement.setLong(5, task.getIssueId());
            preparedStatement.setInt(6, task.getStateId());
            preparedStatement.setTimestamp(7, task.getUpdatedAt());
            preparedStatement.setLong(8, task.getUpdatedUserId());
            preparedStatement.setLong(9, task.getId());
            preparedStatement.executeUpdate();
            return task;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Task delete(Task task) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            task.setActive(false);
            preparedStatement = connect.prepareStatement("update tasks set active = ? where id = ?");
            preparedStatement.setBoolean(1, task.getActive());
            preparedStatement.setLong(2, task.getId());
            preparedStatement.executeUpdate();
            return task;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Task> tasks) throws Exception {
        if (tasks.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Task task : tasks) {
            set.add("" + task.getCreatedUserId());
            set.add("" + task.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Task task : tasks) {
            for (User user : users) {
                if (task.getCreatedUserId() == user.getId())
                    task.setCreatedUser(user);
                if (task.getUpdatedUserId() == user.getId())
                    task.setUpdatedUser(user);
            }
        }
    }

    public void loadIssues(ObservableList<Task> tasks) throws Exception {
        if (tasks.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Task task : tasks) {
            set.add("" + task.getIssueId());
        }

        IssuesRepository ir = new IssuesRepository();
        ObservableList<Issue> issues = ir.getByIds(set);

        for (Task task : tasks) {
            for (Issue issue : issues) {
                if (task.getIssueId() == issue.getId())
                    task.setIssue(issue);
            }
        }
    }

    public void loadStates(ObservableList<Task> tasks) throws Exception {
        if (tasks.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Task task : tasks) {
            set.add("" + task.getStateId());
        }

        TaskStatesRepository tsr = new TaskStatesRepository();
        ObservableList<TaskState> states = tsr.getByIds(set);

        for (Task task : tasks) {
            for (TaskState state : states) {
                if (task.getStateId() == state.getId())
                    task.setState(state);
            }
        }
    }
}
