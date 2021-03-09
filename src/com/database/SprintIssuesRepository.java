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

public class SprintIssuesRepository extends BaseRepository {

    public SprintIssuesRepository() throws Exception {
        super();
    }

    public SprintIssue insert(SprintIssue sprintIssue) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into sprints_issues " +
                            "(issue_id, " +
                            "sprint_id, " +
                            "isClosed, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, sprintIssue.getIssueId());
            preparedStatement.setLong(2, sprintIssue.getSprintId());
            preparedStatement.setBoolean(3, sprintIssue.getIsClosed());
            preparedStatement.setTimestamp(4, sprintIssue.getCreatedAt());
            preparedStatement.setTimestamp(5, sprintIssue.getUpdatedAt());
            preparedStatement.setLong(6, sprintIssue.getCreatedUserId());
            preparedStatement.setLong(7, sprintIssue.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from sprints_issues order by id desc limit 1");
                resultSet.next();
                sprintIssue.setId(resultSet.getInt("id"));
            }
            return sprintIssue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public SprintIssue getById(long id) throws Exception{
        try {
            connect = DriverManager.getConnection(CONN);
            SprintIssue sprintIssue = new SprintIssue();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_issues where id=" + id);
            resultSet.next();
            sprintIssue.setId(resultSet.getInt("id"));
            sprintIssue.setIssueId(resultSet.getLong("issue_id"));
            sprintIssue.setSprintId(resultSet.getLong("sprint_id"));
            sprintIssue.setIsClosed(resultSet.getBoolean("is_closed"));
            sprintIssue.setCreatedAt(resultSet.getTimestamp("created_at"));
            sprintIssue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            sprintIssue.setCreatedUserId(resultSet.getLong("created_user_id"));
            sprintIssue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            sprintIssue.setActive(resultSet.getBoolean("active"));
            return sprintIssue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintIssue> getByIssueId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintIssue> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_issues where active=true and issue_id=" + id);
            while (resultSet.next()) {
                SprintIssue sprintIssue = new SprintIssue();
                sprintIssue.setId(resultSet.getInt("id"));
                sprintIssue.setIssueId(resultSet.getLong("issue_id"));
                sprintIssue.setSprintId(resultSet.getLong("sprint_id"));
                sprintIssue.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintIssue.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintIssue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintIssue.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintIssue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintIssue.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintIssue);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintIssue> getBySprintId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintIssue> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_issues where active=true and sprint_id=" + id);
            while (resultSet.next()) {
                SprintIssue sprintIssue = new SprintIssue();
                sprintIssue.setId(resultSet.getInt("id"));
                sprintIssue.setIssueId(resultSet.getLong("issue_id"));
                sprintIssue.setSprintId(resultSet.getLong("sprint_id"));
                sprintIssue.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintIssue.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintIssue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintIssue.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintIssue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintIssue.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintIssue);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintIssue> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintIssue> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from sprints_issues where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                SprintIssue sprintIssue = new SprintIssue();
                sprintIssue.setId(resultSet.getInt("id"));
                sprintIssue.setIssueId(resultSet.getLong("issue_id"));
                sprintIssue.setSprintId(resultSet.getLong("sprint_id"));
                sprintIssue.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintIssue.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintIssue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintIssue.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintIssue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintIssue.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintIssue);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<SprintIssue> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<SprintIssue> sprintsBacklog = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints_issues where active=true");
            while (resultSet.next()) {
                SprintIssue sprintIssue = new SprintIssue();
                sprintIssue.setId(resultSet.getInt("id"));
                sprintIssue.setIssueId(resultSet.getLong("issue_id"));
                sprintIssue.setSprintId(resultSet.getLong("sprint_id"));
                sprintIssue.setIsClosed(resultSet.getBoolean("is_closed"));
                sprintIssue.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprintIssue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprintIssue.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprintIssue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprintIssue.setActive(resultSet.getBoolean("active"));
                sprintsBacklog.add(sprintIssue);
            }
            return FXCollections.observableList(sprintsBacklog);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public SprintIssue update(SprintIssue sprintIssue) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            sprintIssue.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update sprints_issues set" +
                            " issue_id = ?," +
                            " sprint_id = ?," +
                            " is_closed = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setLong(1, sprintIssue.getIssueId());
            preparedStatement.setLong(2, sprintIssue.getSprintId());
            preparedStatement.setBoolean(3, sprintIssue.getIsClosed());
            preparedStatement.setTimestamp(4, sprintIssue.getUpdatedAt());
            preparedStatement.setLong(5, sprintIssue.getUpdatedUserId());
            preparedStatement.setLong(6, sprintIssue.getId());
            preparedStatement.executeUpdate();
            return sprintIssue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public SprintIssue delete(SprintIssue sprintIssue) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            sprintIssue.setActive(false);
            preparedStatement = connect.prepareStatement("update sprints_issues set active = ? where id = ?");
            preparedStatement.setBoolean(1, sprintIssue.getActive());
            preparedStatement.setLong(2, sprintIssue.getId());
            preparedStatement.executeUpdate();
            return sprintIssue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<SprintIssue> sprintIssues) throws Exception {
        if (sprintIssues.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (SprintIssue sprintIssue : sprintIssues) {
            set.add("" + sprintIssue.getCreatedUserId());
            set.add("" + sprintIssue.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (SprintIssue sprintIssue : sprintIssues) {
            for (User user : users) {
                if (sprintIssue.getCreatedUserId() == user.getId())
                    sprintIssue.setCreatedUser(user);
                if (sprintIssue.getUpdatedUserId() == user.getId())
                    sprintIssue.setUpdatedUser(user);
            }
        }
    }

    public void loadIssues(ObservableList<SprintIssue> sprintIssues) throws Exception {
        if (sprintIssues.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (SprintIssue sprintIssue : sprintIssues) {
            set.add("" + sprintIssue.getIssueId());
        }

        IssuesRepository ir = new IssuesRepository();
        ObservableList<Issue> issues = ir.getByIds(set);

        for (SprintIssue sprintIssue : sprintIssues) {
            for (Issue issue : issues) {
                if (sprintIssue.getIssueId() == issue.getId())
                    sprintIssue.setIssue(issue);
            }
        }
    }

    public void loadSprints(ObservableList<SprintIssue> sprintsIssues) throws Exception {
        if (sprintsIssues.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (SprintIssue sprintIssue : sprintsIssues) {
            set.add("" + sprintIssue.getSprintId());
        }

        SprintsRepository sr = new SprintsRepository();
        ObservableList<Sprint> sprints = sr.getByIds(set);

        for (SprintIssue sprintIssue : sprintsIssues) {
            for (Sprint sprint : sprints) {
                if (sprintIssue.getSprintId() == sprint.getId())
                    sprintIssue.setSprint(sprint);
            }
        }
    }
}
