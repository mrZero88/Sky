package com.database;

import com.base.BaseRepository;
import com.models.IssueState;
import com.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IssueStatesRepository extends BaseRepository {

    public IssueStatesRepository() throws Exception {
        super();
    }

    public IssueState insert(IssueState issueState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into issue_states " +
                            "(title, " +
                            "state, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, issueState.getTitle());
            preparedStatement.setBinaryStream(2, issueState.getState());
            preparedStatement.setTimestamp(3, issueState.getCreatedAt());
            preparedStatement.setTimestamp(4, issueState.getUpdatedAt());
            preparedStatement.setLong(5, issueState.getCreatedUserId());
            preparedStatement.setLong(6, issueState.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from issue_states order by id desc limit 1");
                resultSet.next();
                issueState.setId(resultSet.getInt("id"));
            }
            return issueState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public IssueState getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            IssueState issueState = new IssueState();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from issue_states where id=" + id);
            resultSet.next();
            issueState.setId(resultSet.getInt("id"));
            issueState.setTitle(resultSet.getString("title"));
            issueState.setState(resultSet.getBinaryStream("state"));
            issueState.setCreatedAt(resultSet.getTimestamp("created_at"));
            issueState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            issueState.setCreatedUserId(resultSet.getLong("created_user_id"));
            issueState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            issueState.setActive(resultSet.getBoolean("active"));
            return issueState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<IssueState> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<IssueState> issueStates = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from issue_states where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                IssueState issueState = new IssueState();
                issueState.setId(resultSet.getInt("id"));
                issueState.setTitle(resultSet.getString("title"));
                issueState.setState(resultSet.getBinaryStream("state"));
                issueState.setCreatedAt(resultSet.getTimestamp("created_at"));
                issueState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                issueState.setCreatedUserId(resultSet.getLong("created_user_id"));
                issueState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                issueState.setActive(resultSet.getBoolean("active"));
                issueStates.add(issueState);
            }
            return FXCollections.observableList(issueStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<IssueState> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<IssueState> issueStates = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from issue_states where active=true");
            while (resultSet.next()) {
                IssueState issueState = new IssueState();
                issueState.setId(resultSet.getInt("id"));
                issueState.setTitle(resultSet.getString("title"));
                issueState.setState(resultSet.getBinaryStream("state"));
                issueState.setCreatedAt(resultSet.getTimestamp("created_at"));
                issueState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                issueState.setCreatedUserId(resultSet.getLong("created_user_id"));
                issueState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                issueState.setActive(resultSet.getBoolean("active"));
                issueStates.add(issueState);
            }
            return FXCollections.observableList(issueStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public IssueState update(IssueState issueState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            issueState.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update issue_states set title = ?," +
                            " state = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, issueState.getTitle());
            preparedStatement.setBinaryStream(2, issueState.getState());
            preparedStatement.setTimestamp(3, issueState.getUpdatedAt());
            preparedStatement.setLong(4, issueState.getUpdatedUserId());
            preparedStatement.setLong(5, issueState.getId());
            preparedStatement.executeUpdate();
            return issueState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public IssueState delete(IssueState issueState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            issueState.setActive(false);
            preparedStatement = connect.prepareStatement("update issue_states set active = ? where id = ?");
            preparedStatement.setBoolean(1, issueState.getActive());
            preparedStatement.setLong(2, issueState.getId());
            preparedStatement.executeUpdate();
            return issueState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<IssueState> issueStates) throws Exception {
        if (issueStates.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (IssueState issueState : issueStates) {
            set.add("" + issueState.getCreatedUserId());
            set.add("" + issueState.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (IssueState issueState : issueStates) {
            for (User user : users) {
                if (issueState.getCreatedUserId() == user.getId())
                    issueState.setCreatedUser(user);
                if (issueState.getUpdatedUserId() == user.getId())
                    issueState.setUpdatedUser(user);
            }
        }
    }
}
