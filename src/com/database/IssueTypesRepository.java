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

public class IssueTypesRepository extends BaseRepository {

    public IssueTypesRepository() throws Exception {
        super();
    }

    public IssueType insert(IssueType issueType) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into issue_types " +
                            "(title, " +
                            "description, " +
                            "type, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, issueType.getTitle());
            preparedStatement.setString(2, issueType.getDescription());
            preparedStatement.setBinaryStream(3, issueType.getType());
            preparedStatement.setTimestamp(4, issueType.getCreatedAt());
            preparedStatement.setTimestamp(5, issueType.getUpdatedAt());
            preparedStatement.setLong(6, issueType.getCreatedUserId());
            preparedStatement.setLong(7, issueType.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from issue_types order by id desc limit 1");
                resultSet.next();
                issueType.setId(resultSet.getInt("id"));
            }
            return issueType;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public IssueType getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            IssueType issueType = new IssueType();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from issue_types where id=" + id);
            resultSet.next();
            issueType.setId(resultSet.getInt("id"));
            issueType.setTitle(resultSet.getString("title"));
            issueType.setDescription(resultSet.getString("description"));
            issueType.setType(resultSet.getBinaryStream("type"));
            issueType.setCreatedAt(resultSet.getTimestamp("created_at"));
            issueType.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            issueType.setCreatedUserId(resultSet.getLong("created_user_id"));
            issueType.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            issueType.setActive(resultSet.getBoolean("active"));
            return issueType;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<IssueType> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<IssueType> issueTypes = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from issue_types where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                IssueType issueType = new IssueType();
                issueType.setId(resultSet.getInt("id"));
                issueType.setTitle(resultSet.getString("title"));
                issueType.setDescription(resultSet.getString("description"));
                issueType.setType(resultSet.getBinaryStream("type"));
                issueType.setCreatedAt(resultSet.getTimestamp("created_at"));
                issueType.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                issueType.setCreatedUserId(resultSet.getLong("created_user_id"));
                issueType.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                issueType.setActive(resultSet.getBoolean("active"));
                issueTypes.add(issueType);
            }
            return FXCollections.observableList(issueTypes);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<IssueType> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<IssueType> issueTypes = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from issue_types where active=true");
            while (resultSet.next()) {
                IssueType issueType = new IssueType();
                issueType.setId(resultSet.getInt("id"));
                issueType.setTitle(resultSet.getString("title"));
                issueType.setDescription(resultSet.getString("description"));
                issueType.setType(resultSet.getBinaryStream("type"));
                issueType.setCreatedAt(resultSet.getTimestamp("created_at"));
                issueType.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                issueType.setCreatedUserId(resultSet.getLong("created_user_id"));
                issueType.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                issueType.setActive(resultSet.getBoolean("active"));
                issueTypes.add(issueType);
            }
            return FXCollections.observableList(issueTypes);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public IssueType update(IssueType issueType) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            issueType.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update issue_types set title = ?," +
                            " description = ?, " +
                            " type = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, issueType.getTitle());
            preparedStatement.setString(2, issueType.getDescription());
            preparedStatement.setBinaryStream(3, issueType.getType());
            preparedStatement.setTimestamp(4, issueType.getUpdatedAt());
            preparedStatement.setLong(5, issueType.getUpdatedUserId());
            preparedStatement.setLong(6, issueType.getId());
            preparedStatement.executeUpdate();
            return issueType;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public IssueType delete(IssueType issueType) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            issueType.setActive(false);
            preparedStatement = connect.prepareStatement("update issue_types set active = ? where id = ?");
            preparedStatement.setBoolean(1, issueType.getActive());
            preparedStatement.setLong(2, issueType.getId());
            preparedStatement.executeUpdate();
            return issueType;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<BugState> bugStates) throws Exception {
        if (bugStates.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (BugState bugState : bugStates) {
            set.add("" + bugState.getCreatedUserId());
            set.add("" + bugState.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (BugState bugState : bugStates) {
            for (User user : users) {
                if (bugState.getCreatedUserId() == user.getId())
                    bugState.setCreatedUser(user);
                if (bugState.getUpdatedUserId() == user.getId())
                    bugState.setUpdatedUser(user);
            }
        }
    }
}
