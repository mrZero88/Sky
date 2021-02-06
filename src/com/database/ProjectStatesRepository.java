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

public class ProjectStatesRepository extends BaseRepository {

    public ProjectStatesRepository() throws Exception {
        super();
    }

    public ProjectState insert(ProjectState projectState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into project_states " +
                            "(title, " +
                            "state, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, projectState.getTitle());
            preparedStatement.setBinaryStream(2, projectState.getState());
            preparedStatement.setTimestamp(3, projectState.getCreatedAt());
            preparedStatement.setTimestamp(4, projectState.getUpdatedAt());
            preparedStatement.setLong(5, projectState.getCreatedUserId());
            preparedStatement.setLong(6, projectState.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from project_states order by id desc limit 1");
                resultSet.next();
                projectState.setId(resultSet.getInt("id"));
            }
            return projectState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ProjectState getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            ProjectState projectState = new ProjectState();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from project_states where id=" + id);
            resultSet.next();
            projectState.setId(resultSet.getInt("id"));
            projectState.setTitle(resultSet.getString("title"));
            projectState.setState(resultSet.getBinaryStream("state"));
            projectState.setCreatedAt(resultSet.getTimestamp("created_at"));
            projectState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            projectState.setCreatedUserId(resultSet.getLong("created_user_id"));
            projectState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            projectState.setActive(resultSet.getBoolean("active"));
            return projectState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<ProjectState> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<ProjectState> projectStates = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from project_states where active=true");
            while (resultSet.next()) {
                ProjectState projectState = new ProjectState();
                projectState.setId(resultSet.getInt("id"));
                projectState.setTitle(resultSet.getString("title"));
                projectState.setState(resultSet.getBinaryStream("state"));
                projectState.setCreatedAt(resultSet.getTimestamp("created_at"));
                projectState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                projectState.setCreatedUserId(resultSet.getLong("created_user_id"));
                projectState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                projectState.setActive(resultSet.getBoolean("active"));
                projectStates.add(projectState);
            }
            return FXCollections.observableList(projectStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ProjectState update(ProjectState projectState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            projectState.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update project_states set title = ?," +
                            " state = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, projectState.getTitle());
            preparedStatement.setBinaryStream(2, projectState.getState());
            preparedStatement.setTimestamp(3, projectState.getUpdatedAt());
            preparedStatement.setLong(4, projectState.getUpdatedUserId());
            preparedStatement.setLong(5, projectState.getId());
            preparedStatement.executeUpdate();
            return projectState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ProjectState delete(ProjectState projectState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            projectState.setActive(false);
            preparedStatement = connect.prepareStatement("update project_states set active = ? where id = ?");
            preparedStatement.setBoolean(1, projectState.getActive());
            preparedStatement.setLong(2, projectState.getId());
            preparedStatement.executeUpdate();
            return projectState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<ProjectState> loadCreatedUsers(ObservableList<ProjectState> projectStates) throws Exception {
        if (projectStates.isEmpty())
            return projectStates;

        try {
            connect = DriverManager.getConnection(CONN);
            for (ProjectState projectState : projectStates) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + projectState.getCreatedUserId());
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
                    projectState.setCreatedUser(user);
                }
            }
            return projectStates;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<ProjectState> loadUpdatedUsers(ObservableList<ProjectState> projectStates) throws Exception {
        if (projectStates.isEmpty())
            return projectStates;

        try {
            connect = DriverManager.getConnection(CONN);
            for (ProjectState projectState : projectStates) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + projectState.getUpdatedUserId());
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
                    projectState.setUpdatedUser(user);
                }
            }
            return projectStates;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
