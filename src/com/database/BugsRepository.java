package com.database;

import com.models.Bug;
import com.models.BugState;
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

public class BugsRepository extends BaseRepository {

    public BugsRepository() throws Exception {
        super();
    }

    public Bug insert(Bug bug) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into bugs " +
                            "(title, " +
                            "description, " +
                            "project_id, " +
                            "state_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, bug.getTitle());
            preparedStatement.setString(2, bug.getDescription());
            preparedStatement.setLong(3, bug.getProjectId());
            preparedStatement.setLong(4, bug.getStateId());
            preparedStatement.setTimestamp(5, bug.getCreatedAt());
            preparedStatement.setTimestamp(6, bug.getUpdatedAt());
            preparedStatement.setLong(7, bug.getCreatedUserId());
            preparedStatement.setLong(8, bug.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from bugs order by id desc limit 1");
                resultSet.next();
                bug.setId(resultSet.getInt("id"));
            }
            return bug;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Bug getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Bug bug = new Bug();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from bugs where id=" + id);
            resultSet.next();
            bug.setId(resultSet.getLong("id"));
            bug.setTitle(resultSet.getString("title"));
            bug.setDescription(resultSet.getString("description"));
            bug.setProjectId(resultSet.getLong("project_id"));
            bug.setStateId(resultSet.getInt("state_id"));
            bug.setCreatedAt(resultSet.getTimestamp("created_at"));
            bug.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            bug.setCreatedUserId(resultSet.getLong("created_user_id"));
            bug.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            bug.setActive(resultSet.getBoolean("active"));
            return bug;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Bug> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Bug> bugs = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from bugs where active=true");
            while (resultSet.next()) {
                Bug bug = new Bug();
                bug.setId(resultSet.getLong("id"));
                bug.setTitle(resultSet.getString("title"));
                bug.setDescription(resultSet.getString("description"));
                bug.setProjectId(resultSet.getLong("project_id"));
                bug.setStateId(resultSet.getInt("state_id"));
                bug.setCreatedAt(resultSet.getTimestamp("created_at"));
                bug.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                bug.setCreatedUserId(resultSet.getLong("created_user_id"));
                bug.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                bug.setActive(resultSet.getBoolean("active"));
                bugs.add(bug);
            }
            return FXCollections.observableList(bugs);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Bug update(Bug bug) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            bug.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update bugs set title = ?," +
                            " description = ?," +
                            " project_id = ?, " +
                            " state_id = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, bug.getTitle());
            preparedStatement.setString(2, bug.getDescription());
            preparedStatement.setLong(3, bug.getProjectId());
            preparedStatement.setInt(4, bug.getStateId());
            preparedStatement.setTimestamp(5, bug.getUpdatedAt());
            preparedStatement.setLong(6, bug.getUpdatedUserId());
            preparedStatement.setLong(7, bug.getId());
            preparedStatement.executeUpdate();
            return bug;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Bug delete(Bug bug) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            bug.setActive(false);
            preparedStatement = connect.prepareStatement("update bugs set active = ? where id = ?");
            preparedStatement.setBoolean(1, bug.getActive());
            preparedStatement.setLong(2, bug.getId());
            preparedStatement.executeUpdate();
            return bug;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Bug> loadCreatedUsers(ObservableList<Bug> bugs) throws Exception {
        if (bugs.isEmpty())
            return bugs;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Bug bug : bugs) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + bug.getCreatedUserId());
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
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    bug.setCreatedUser(user);
                }
            }
            return bugs;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Bug> loadUpdatedUsers(ObservableList<Bug> bugs) throws Exception {
        if (bugs.isEmpty())
            return bugs;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Bug bug : bugs) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + bug.getUpdatedUserId());
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
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    bug.setUpdatedUser(user);
                }
            }
            return bugs;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Bug> loadBugStates(ObservableList<Bug> bugs) throws Exception {
        if (bugs.isEmpty())
            return bugs;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Bug bug : bugs) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from bug_states where active=true and id=" + bug.getStateId());
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
                    bug.setState(bugState);
                }
            }
            return bugs;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
