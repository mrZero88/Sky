package com.database;

import com.models.Bug;
import com.models.BugState;
import com.models.TaskState;
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

public class BugStatesRepository extends BaseRepository {

    public BugStatesRepository() throws Exception {
        super();
    }

    public BugState insert(BugState bugState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into bug_states " +
                            "(title, " +
                            "state, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, bugState.getTitle());
            preparedStatement.setBinaryStream(2, bugState.getState());
            preparedStatement.setTimestamp(3, bugState.getCreatedAt());
            preparedStatement.setTimestamp(4, bugState.getUpdatedAt());
            preparedStatement.setLong(5, bugState.getCreatedUserId());
            preparedStatement.setLong(6, bugState.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from bug_states order by id desc limit 1");
                resultSet.next();
                bugState.setId(resultSet.getInt("id"));
            }
            return bugState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public BugState getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            BugState bugState = new BugState();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from bug_states where id=" + id);
            resultSet.next();
            bugState.setId(resultSet.getInt("id"));
            bugState.setTitle(resultSet.getString("title"));
            bugState.setState(resultSet.getBinaryStream("state"));
            bugState.setCreatedAt(resultSet.getTimestamp("created_at"));
            bugState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            bugState.setCreatedUserId(resultSet.getLong("created_user_id"));
            bugState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            bugState.setActive(resultSet.getBoolean("active"));
            return bugState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<BugState> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<BugState> bugStates = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from bug_states where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                BugState bugState = new BugState();
                bugState.setId(resultSet.getInt("id"));
                bugState.setTitle(resultSet.getString("title"));
                bugState.setState(resultSet.getBinaryStream("state"));
                bugState.setCreatedAt(resultSet.getTimestamp("created_at"));
                bugState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                bugState.setCreatedUserId(resultSet.getLong("created_user_id"));
                bugState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                bugState.setActive(resultSet.getBoolean("active"));
                bugStates.add(bugState);
            }
            return FXCollections.observableList(bugStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<BugState> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<BugState> bugStates = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from bug_states where active=true");
            while (resultSet.next()) {
                BugState bugState = new BugState();
                bugState.setId(resultSet.getInt("id"));
                bugState.setTitle(resultSet.getString("title"));
                bugState.setState(resultSet.getBinaryStream("state"));
                bugState.setCreatedAt(resultSet.getTimestamp("created_at"));
                bugState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                bugState.setCreatedUserId(resultSet.getLong("created_user_id"));
                bugState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                bugState.setActive(resultSet.getBoolean("active"));
                bugStates.add(bugState);
            }
            return FXCollections.observableList(bugStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public BugState update(BugState bugState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            bugState.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update bug_states set title = ?," +
                            " state = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, bugState.getTitle());
            preparedStatement.setBinaryStream(2, bugState.getState());
            preparedStatement.setTimestamp(3, bugState.getUpdatedAt());
            preparedStatement.setLong(4, bugState.getUpdatedUserId());
            preparedStatement.setLong(5, bugState.getId());
            preparedStatement.executeUpdate();
            return bugState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public BugState delete(BugState bugState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            bugState.setActive(false);
            preparedStatement = connect.prepareStatement("update bug_states set active = ? where id = ?");
            preparedStatement.setBoolean(1, bugState.getActive());
            preparedStatement.setLong(2, bugState.getId());
            preparedStatement.executeUpdate();
            return bugState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<BugState> bugStates) throws Exception {
        if(bugStates.isEmpty())
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

    protected void closeConnections() throws Exception {
        if (this.preparedStatement != null)
            this.preparedStatement.close();
        if (this.statement != null)
            this.statement.close();
        if (this.resultSet != null)
            this.resultSet.close();
    }
}
