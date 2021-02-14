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

public class FeaturesRepository extends BaseRepository {

    public FeaturesRepository() throws Exception {
        super();
    }

    public Feature insert(Feature feature) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into features " +
                            "(title, " +
                            "description, " +
                            "project_id, " +
                            "state_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, feature.getTitle());
            preparedStatement.setString(2, feature.getDescription());
            preparedStatement.setLong(3, feature.getProjectId());
            preparedStatement.setInt(4, feature.getStateId());
            preparedStatement.setTimestamp(5, feature.getCreatedAt());
            preparedStatement.setTimestamp(6, feature.getUpdatedAt());
            preparedStatement.setLong(7, feature.getCreatedUserId());
            preparedStatement.setLong(8, feature.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from features order by id desc limit 1");
                resultSet.next();
                feature.setId(resultSet.getInt("id"));
            }
            return feature;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Feature getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Feature feature = new Feature();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from features where id=" + id);
            resultSet.next();
            feature.setId(resultSet.getLong("id"));
            feature.setTitle(resultSet.getString("title"));
            feature.setDescription(resultSet.getString("description"));
            feature.setProjectId(resultSet.getLong("project_id"));
            feature.setStateId(resultSet.getInt("state_id"));
            feature.setCreatedAt(resultSet.getTimestamp("created_at"));
            feature.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            feature.setCreatedUserId(resultSet.getLong("created_user_id"));
            feature.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            feature.setActive(resultSet.getBoolean("active"));
            return feature;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Feature> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Feature> features = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from features where active=true");
            while (resultSet.next()) {
                Feature feature = new Feature();
                feature.setId(resultSet.getLong("id"));
                feature.setTitle(resultSet.getString("title"));
                feature.setDescription(resultSet.getString("description"));
                feature.setProjectId(resultSet.getLong("project_id"));
                feature.setStateId(resultSet.getInt("state_id"));
                feature.setCreatedAt(resultSet.getTimestamp("created_at"));
                feature.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                feature.setCreatedUserId(resultSet.getLong("created_user_id"));
                feature.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                feature.setActive(resultSet.getBoolean("active"));
                features.add(feature);
            }
            return FXCollections.observableList(features);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Feature update(Feature feature) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            feature.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update features set title = ?," +
                            " description = ?," +
                            " project_id = ?, " +
                            " state_id = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, feature.getTitle());
            preparedStatement.setString(2, feature.getDescription());
            preparedStatement.setLong(3, feature.getProjectId());
            preparedStatement.setInt(4, feature.getStateId());
            preparedStatement.setTimestamp(5, feature.getUpdatedAt());
            preparedStatement.setLong(6, feature.getUpdatedUserId());
            preparedStatement.setLong(7, feature.getId());
            preparedStatement.executeUpdate();
            return feature;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Feature delete(Feature feature) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            feature.setActive(false);
            preparedStatement = connect.prepareStatement("update features set active = ? where id = ?");
            preparedStatement.setBoolean(1, feature.getActive());
            preparedStatement.setLong(2, feature.getId());
            preparedStatement.executeUpdate();
            return feature;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Feature> features) throws Exception {
        if (features.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Feature feature : features) {
            set.add("" + feature.getCreatedUserId());
            set.add("" + feature.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Feature feature : features) {
            for (User user : users) {
                if (feature.getCreatedUserId() == user.getId())
                    feature.setCreatedUser(user);
                if (feature.getUpdatedUserId() == user.getId())
                    feature.setUpdatedUser(user);
            }
        }
    }

    public void loadStates(ObservableList<Feature> features) throws Exception {
        if (features.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Feature feature : features) {
            set.add("" + feature.getStateId());
        }

        FeatureStatesRepository fsr = new FeatureStatesRepository();
        ObservableList<FeatureState> featureStates = fsr.getByIds(set);

        for (Feature feature : features) {
            for (FeatureState featureState : featureStates) {
                if (feature.getStateId() == featureState.getId())
                    feature.setState(featureState);
            }
        }
    }
}
