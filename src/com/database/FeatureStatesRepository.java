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

public class FeatureStatesRepository extends BaseRepository {

    public FeatureStatesRepository() throws Exception {
        super();
    }

    public FeatureState insert(FeatureState featureState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into feature_states " +
                            "(title, " +
                            "state, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, featureState.getTitle());
            preparedStatement.setBinaryStream(2, featureState.getState());
            preparedStatement.setTimestamp(3, featureState.getCreatedAt());
            preparedStatement.setTimestamp(4, featureState.getUpdatedAt());
            preparedStatement.setLong(5, featureState.getCreatedUserId());
            preparedStatement.setLong(6, featureState.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from feature_states order by id desc limit 1");
                resultSet.next();
                featureState.setId(resultSet.getInt("id"));
            }
            return featureState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public FeatureState getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            FeatureState featureState = new FeatureState();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from feature_states where id=" + id);
            resultSet.next();
            featureState.setId(resultSet.getInt("id"));
            featureState.setTitle(resultSet.getString("title"));
            featureState.setState(resultSet.getBinaryStream("state"));
            featureState.setCreatedAt(resultSet.getTimestamp("created_at"));
            featureState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            featureState.setCreatedUserId(resultSet.getLong("created_user_id"));
            featureState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            featureState.setActive(resultSet.getBoolean("active"));
            return featureState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<FeatureState> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<FeatureState> featureStates = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from feature_states where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                FeatureState featureState = new FeatureState();
                featureState.setId(resultSet.getInt("id"));
                featureState.setTitle(resultSet.getString("title"));
                featureState.setState(resultSet.getBinaryStream("state"));
                featureState.setCreatedAt(resultSet.getTimestamp("created_at"));
                featureState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                featureState.setCreatedUserId(resultSet.getLong("created_user_id"));
                featureState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                featureState.setActive(resultSet.getBoolean("active"));
                featureStates.add(featureState);
            }
            return FXCollections.observableList(featureStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<FeatureState> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<FeatureState> featureStates = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from feature_states where active=true");
            while (resultSet.next()) {
                FeatureState featureState = new FeatureState();
                featureState.setId(resultSet.getInt("id"));
                featureState.setTitle(resultSet.getString("title"));
                featureState.setState(resultSet.getBinaryStream("state"));
                featureState.setCreatedAt(resultSet.getTimestamp("created_at"));
                featureState.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                featureState.setCreatedUserId(resultSet.getLong("created_user_id"));
                featureState.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                featureState.setActive(resultSet.getBoolean("active"));
                featureStates.add(featureState);
            }
            return FXCollections.observableList(featureStates);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public FeatureState update(FeatureState featureState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            featureState.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update feature_states set title = ?," +
                            " state = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, featureState.getTitle());
            preparedStatement.setBinaryStream(2, featureState.getState());
            preparedStatement.setTimestamp(3, featureState.getUpdatedAt());
            preparedStatement.setLong(4, featureState.getUpdatedUserId());
            preparedStatement.setLong(5, featureState.getId());
            preparedStatement.executeUpdate();
            return featureState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public FeatureState delete(FeatureState featureState) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            featureState.setActive(false);
            preparedStatement = connect.prepareStatement("update feature_states set active = ? where id = ?");
            preparedStatement.setBoolean(1, featureState.getActive());
            preparedStatement.setLong(2, featureState.getId());
            preparedStatement.executeUpdate();
            return featureState;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<FeatureState> loadCreatedUsers(ObservableList<FeatureState> featureStates) throws Exception {
        if (featureStates.isEmpty())
            return featureStates;

        try {
            connect = DriverManager.getConnection(CONN);
            for (FeatureState featureState : featureStates) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + featureState.getCreatedUserId());
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
                    featureState.setCreatedUser(user);
                }
            }
            return featureStates;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<FeatureState> loadUpdatedUsers(ObservableList<FeatureState> featureStates) throws Exception {
        if (featureStates.isEmpty())
            return featureStates;

        try {
            connect = DriverManager.getConnection(CONN);
            for (FeatureState featureState : featureStates) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + featureState.getUpdatedUserId());
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
                    featureState.setUpdatedUser(user);
                }
            }
            return featureStates;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
