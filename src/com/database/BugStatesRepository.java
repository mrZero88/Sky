package com.database;

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

    public ObservableList<BugState> loadCreatedUsers(ObservableList<BugState> bugStates) throws Exception {
        if (bugStates.isEmpty())
            return bugStates;

        try {
            connect = DriverManager.getConnection(CONN);
            for (BugState bugState : bugStates) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + bugState.getCreatedUserId());
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                    user.setPassword(resultSet.getString("password"));
                    user.setTypeId(resultSet.getByte("type_id"));
                    user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                    user.setCountryId(resultSet.getInt("country_id"));
                    user.setPicture(resultSet.getBinaryStream("picture"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setAdress(resultSet.getString("adress"));
                    user.setPostalCode(resultSet.getString("postal_code"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setRememberToken(resultSet.getString("remember_token"));
                    user.setGenderId(resultSet.getInt("gender_id"));
                    user.setAbbreviation(resultSet.getString("abbreviation"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    bugState.setCreatedUser(user);
                }
            }
            return bugStates;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<BugState> loadUpdatedUsers(ObservableList<BugState> bugStates) throws Exception {
        if (bugStates.isEmpty())
            return bugStates;

        try {
            connect = DriverManager.getConnection(CONN);
            for (BugState bugState : bugStates) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + bugState.getUpdatedUserId());
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                    user.setPassword(resultSet.getString("password"));
                    user.setTypeId(resultSet.getByte("type_id"));
                    user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                    user.setCountryId(resultSet.getInt("country_id"));
                    user.setPicture(resultSet.getBinaryStream("picture"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setAdress(resultSet.getString("adress"));
                    user.setPostalCode(resultSet.getString("postal_code"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setRememberToken(resultSet.getString("remember_token"));
                    user.setGenderId(resultSet.getInt("gender_id"));
                    user.setAbbreviation(resultSet.getString("abbreviation"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    bugState.setUpdatedUser(user);
                }
            }
            return bugStates;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
