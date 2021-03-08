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

public class SprintsBacklogRepository extends BaseRepository {

    public SprintsBacklogRepository() throws Exception {
        super();
    }

    public SprintBacklog insert(SprintBacklog sprintBacklog) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into sprints_backlog " +
                            "(issue_id, " +
                            "sprint_id, " +
                            "isClosed, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, sprintBacklog.getIssueId());
            preparedStatement.setLong(2, sprintBacklog.getSprintId());
            preparedStatement.setBoolean(3, sprintBacklog.getIsClosed());
            preparedStatement.setTimestamp(4, sprintBacklog.getCreatedAt());
            preparedStatement.setTimestamp(5, sprintBacklog.getUpdatedAt());
            preparedStatement.setLong(6, sprintBacklog.getCreatedUserId());
            preparedStatement.setLong(7, sprintBacklog.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from sprints_backlog order by id desc limit 1");
                resultSet.next();
                sprintBacklog.setId(resultSet.getInt("id"));
            }
            return sprintBacklog;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public SprintBacklog getById(long id) throws Exception{
        try {
            connect = DriverManager.getConnection(CONN);
            SprintBacklog sprintBacklog = new SprintBacklog();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_backlog where id=" + id);
            resultSet.next();
            sprintBacklog.setId(resultSet.getInt("id"));
            sprintBacklog.setIssueId(resultSet.getLong("issue_id"));
            sprintBacklog.setSprintId(resultSet.getLong("sprint_id"));
            sprintBacklog.setIsClosed(resultSet.getBoolean("is_closed"));
            sprintBacklog.setCreatedAt(resultSet.getTimestamp("created_at"));
            sprintBacklog.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            sprintBacklog.setCreatedUserId(resultSet.getLong("created_user_id"));
            sprintBacklog.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            sprintBacklog.setActive(resultSet.getBoolean("active"));
            return sprintBacklog;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintBacklog> getByIssueId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintBacklog> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_backlog where active=true and issue_id=" + id);
            while (resultSet.next()) {
                SprintBacklog sprintBacklog = new SprintBacklog();
                sprintBacklog.setId(resultSet.getInt("id"));
                sprintBacklog.setIssueId(resultSet.getLong("issue_id"));
                sprintBacklog.setSprintId(resultSet.getLong("sprint_id"));
                sprintBacklog.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintBacklog.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintBacklog.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintBacklog.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintBacklog.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintBacklog.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintBacklog);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintBacklog> getBySprintId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintBacklog> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_backlog where active=true and sprint_id=" + id);
            while (resultSet.next()) {
                SprintBacklog sprintBacklog = new SprintBacklog();
                sprintBacklog.setId(resultSet.getInt("id"));
                sprintBacklog.setIssueId(resultSet.getLong("issue_id"));
                sprintBacklog.setSprintId(resultSet.getLong("sprint_id"));
                sprintBacklog.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintBacklog.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintBacklog.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintBacklog.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintBacklog.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintBacklog.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintBacklog);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintBacklog> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintBacklog> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from sprints_backlog where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                SprintBacklog sprintBacklog = new SprintBacklog();
                sprintBacklog.setId(resultSet.getInt("id"));
                sprintBacklog.setIssueId(resultSet.getLong("issue_id"));
                sprintBacklog.setSprintId(resultSet.getLong("sprint_id"));
                sprintBacklog.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintBacklog.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintBacklog.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintBacklog.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintBacklog.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintBacklog.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintBacklog);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintBacklog> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintBacklog> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_backlog where active=true");
            while (resultSet.next()) {
                SprintBacklog sprintBacklog = new SprintBacklog();
                sprintBacklog.setId(resultSet.getInt("id"));
                sprintBacklog.setIssueId(resultSet.getLong("issue_id"));
                sprintBacklog.setSprintId(resultSet.getLong("sprint_id"));
                sprintBacklog.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintBacklog.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintBacklog.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintBacklog.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintBacklog.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintBacklog.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintBacklog);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public SprintBacklog update(SprintBacklog sprintBacklog) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            sprintBacklog.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update sprints_backlog set" +
                            " issue_id = ?," +
                            " sprint_id = ?," +
                            " is_closed = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setLong(1, sprintBacklog.getIssueId());
            preparedStatement.setLong(2, sprintBacklog.getSprintId());
            preparedStatement.setBoolean(3, sprintBacklog.getIsClosed());
            preparedStatement.setTimestamp(4, sprintBacklog.getUpdatedAt());
            preparedStatement.setLong(5, sprintBacklog.getUpdatedUserId());
            preparedStatement.setLong(6, sprintBacklog.getId());
            preparedStatement.executeUpdate();
            return sprintBacklog;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public SprintBacklog delete(SprintBacklog sprintBacklog) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            sprintBacklog.setActive(false);
            preparedStatement = connect.prepareStatement("update sprints_backlog set active = ? where id = ?");
            preparedStatement.setBoolean(1, sprintBacklog.getActive());
            preparedStatement.setLong(2, sprintBacklog.getId());
            preparedStatement.executeUpdate();
            return sprintBacklog;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<SprintBacklog> sprintsBacklog) throws Exception {
        if (sprintsBacklog.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (SprintBacklog sprintBacklog : sprintsBacklog) {
            set.add("" + sprintBacklog.getCreatedUserId());
            set.add("" + sprintBacklog.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (SprintBacklog sprintBacklog : sprintsBacklog) {
            for (User user : users) {
                if (sprintBacklog.getCreatedUserId() == user.getId())
                    sprintBacklog.setCreatedUser(user);
                if (sprintBacklog.getUpdatedUserId() == user.getId())
                    sprintBacklog.setUpdatedUser(user);
            }
        }
    }

    public void loadIssues(ObservableList<SprintBacklog> sprintsBacklog) throws Exception {
        if (sprintsBacklog.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (SprintBacklog sprintBacklog : sprintsBacklog) {
            set.add("" + sprintBacklog.getIssueId());
        }

        IssuesRepository ir = new IssuesRepository();
        ObservableList<Issue> issues = ir.getByIds(set);

        for (SprintBacklog sprintBacklog : sprintsBacklog) {
            for (Issue issue : issues) {
                if (sprintBacklog.getIssueId() == issue.getId())
                    sprintBacklog.setIssue(issue);
            }
        }
    }

    public void loadSprints(ObservableList<SprintBacklog> sprintsBacklog) throws Exception {
        if (sprintsBacklog.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (SprintBacklog sprintBacklog : sprintsBacklog) {
            set.add("" + sprintBacklog.getSprintId());
        }

        SprintsRepository sr = new SprintsRepository();
        ObservableList<Sprint> sprints = sr.getByIds(set);

        for (SprintBacklog sprintBacklog : sprintsBacklog) {
            for (Sprint sprint : sprints) {
                if (sprintBacklog.getSprintId() == sprint.getId())
                    sprintBacklog.setSprint(sprint);
            }
        }
    }
}
