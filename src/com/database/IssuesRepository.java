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

public class IssuesRepository extends BaseRepository {

    public IssuesRepository() throws Exception {
        super();
    }

    public Issue insert(Issue issue) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into issues " +
                            "(title, " +
                            "description, " +
                            "project_id, " +
                            "type_id, " +
                            "state_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, issue.getTitle());
            preparedStatement.setString(2, issue.getDescription());
            preparedStatement.setLong(3, issue.getProjectId());
            preparedStatement.setInt(4, issue.getTypeId());
            preparedStatement.setLong(5, issue.getStateId());
            preparedStatement.setTimestamp(6, issue.getCreatedAt());
            preparedStatement.setTimestamp(7, issue.getUpdatedAt());
            preparedStatement.setLong(8, issue.getCreatedUserId());
            preparedStatement.setLong(9, issue.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from issues order by id desc limit 1");
                resultSet.next();
                issue.setId(resultSet.getInt("id"));
            }
            return issue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Issue getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Issue issue = new Issue();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from issues where id=" + id);
            resultSet.next();
            issue.setId(resultSet.getLong("id"));
            issue.setTitle(resultSet.getString("title"));
            issue.setDescription(resultSet.getString("description"));
            issue.setProjectId(resultSet.getLong("project_id"));
            issue.setTypeId(resultSet.getInt("type_id"));
            issue.setStateId(resultSet.getInt("state_id"));
            issue.setCreatedAt(resultSet.getTimestamp("created_at"));
            issue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            issue.setCreatedUserId(resultSet.getLong("created_user_id"));
            issue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            issue.setActive(resultSet.getBoolean("active"));
            return issue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Issue> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Issue> issues = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from issues where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Issue issue = new Issue();
                issue.setId(resultSet.getLong("id"));
                issue.setTitle(resultSet.getString("title"));
                issue.setDescription(resultSet.getString("description"));
                issue.setProjectId(resultSet.getLong("project_id"));
                issue.setTypeId(resultSet.getInt("type_id"));
                issue.setStateId(resultSet.getInt("state_id"));
                issue.setCreatedAt(resultSet.getTimestamp("created_at"));
                issue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                issue.setCreatedUserId(resultSet.getLong("created_user_id"));
                issue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                issue.setActive(resultSet.getBoolean("active"));
                issues.add(issue);
            }
            return FXCollections.observableList(issues);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Issue> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Issue> issues = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from issues where active=true");
            while (resultSet.next()) {
                Issue issue = new Issue();
                issue.setId(resultSet.getLong("id"));
                issue.setTitle(resultSet.getString("title"));
                issue.setDescription(resultSet.getString("description"));
                issue.setProjectId(resultSet.getLong("project_id"));
                issue.setTypeId(resultSet.getInt("type_id"));
                issue.setStateId(resultSet.getInt("state_id"));
                issue.setCreatedAt(resultSet.getTimestamp("created_at"));
                issue.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                issue.setCreatedUserId(resultSet.getLong("created_user_id"));
                issue.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                issue.setActive(resultSet.getBoolean("active"));
                issues.add(issue);
            }
            return FXCollections.observableList(issues);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Issue update(Issue issue) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            issue.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update issues set title = ?," +
                            " description = ?," +
                            " project_id = ?, " +
                            " type_id = ?, " +
                            " state_id = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, issue.getTitle());
            preparedStatement.setString(2, issue.getDescription());
            preparedStatement.setLong(3, issue.getProjectId());
            preparedStatement.setInt(4, issue.getTypeId());
            preparedStatement.setInt(5, issue.getStateId());
            preparedStatement.setTimestamp(6, issue.getUpdatedAt());
            preparedStatement.setLong(7, issue.getUpdatedUserId());
            preparedStatement.setLong(8, issue.getId());
            preparedStatement.executeUpdate();
            return issue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Issue delete(Issue issue) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            issue.setActive(false);
            preparedStatement = connect.prepareStatement("update issues set active = ? where id = ?");
            preparedStatement.setBoolean(1, issue.getActive());
            preparedStatement.setLong(2, issue.getId());
            preparedStatement.executeUpdate();
            return issue;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Issue> issues) throws Exception {
        if (issues.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Issue issue : issues) {
            set.add("" + issue.getCreatedUserId());
            set.add("" + issue.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Issue issue : issues) {
            for (User user : users) {
                if (issue.getCreatedUserId() == user.getId())
                    issue.setCreatedUser(user);
                if (issue.getUpdatedUserId() == user.getId())
                    issue.setUpdatedUser(user);
            }
        }
    }

    public void loadStates(ObservableList<Issue> issues) throws Exception {
        if (issues.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Issue issue : issues) {
            set.add("" + issue.getStateId());
        }

        IssueStatesRepository isr = new IssueStatesRepository();
        ObservableList<IssueState> issueStates = isr.getByIds(set);

        for (Issue issue : issues) {
            for (IssueState issueState : issueStates) {
                if (issue.getStateId() == issueState.getId())
                    issue.setState(issueState);
            }
        }
    }

    public void loadProjects(ObservableList<Issue> issues) throws Exception {
        if (issues.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Issue issue : issues) {
            set.add("" + issue.getProjectId());
        }

        ProjectsRepository pr = new ProjectsRepository();
        ObservableList<Project> projects = pr.getByIds(set);

        for (Issue issue : issues) {
            for (Project project : projects) {
                if (issue.getStateId() == project.getId())
                    issue.setProject(project);
            }
        }
    }
}
