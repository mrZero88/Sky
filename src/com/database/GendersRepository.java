package com.database;

import com.models.FeatureState;
import com.models.Gender;
import com.models.ProjectState;
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

public class GendersRepository extends BaseRepository {

    public GendersRepository() throws Exception {
        super();
    }

    public Gender insert(Gender gender) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into genders " +
                            "(name, " +
                            "shortcut, " +
                            "gender, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, gender.getName());
            preparedStatement.setString(2, gender.getShortcut());
            preparedStatement.setBinaryStream(3, gender.getGender());
            preparedStatement.setTimestamp(4, gender.getCreatedAt());
            preparedStatement.setTimestamp(5, gender.getUpdatedAt());
            preparedStatement.setLong(6, gender.getCreatedUserId());
            preparedStatement.setLong(7, gender.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from genders order by id desc limit 1");
                resultSet.next();
                gender.setId(resultSet.getInt("id"));
            }
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Gender getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Gender gender = new Gender();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from genders where id=" + id);
            resultSet.next();
            gender.setId(resultSet.getInt("id"));
            gender.setName(resultSet.getString("name"));
            gender.setShortcut(resultSet.getString("shortcut"));
            gender.setGender(resultSet.getBinaryStream("gender"));
            gender.setCreatedAt(resultSet.getTimestamp("created_at"));
            gender.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            gender.setCreatedUserId(resultSet.getLong("created_user_id"));
            gender.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            gender.setActive(resultSet.getBoolean("active"));
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Gender> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Gender> genders = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from genders where active=true");
            while (resultSet.next()) {
                Gender gender = new Gender();
                gender.setId(resultSet.getInt("id"));
                gender.setName(resultSet.getString("name"));
                gender.setShortcut(resultSet.getString("shortcut"));
                gender.setGender(resultSet.getBinaryStream("gender"));
                gender.setCreatedAt(resultSet.getTimestamp("created_at"));
                gender.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                gender.setCreatedUserId(resultSet.getLong("created_user_id"));
                gender.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                gender.setActive(resultSet.getBoolean("active"));
                genders.add(gender);
            }
            return FXCollections.observableList(genders);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Gender update(Gender gender) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            gender.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update genders set name = ?," +
                            " shortcut = ?, " +
                            " gender = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, gender.getName());
            preparedStatement.setString(2, gender.getShortcut());
            preparedStatement.setBinaryStream(3, gender.getGender());
            preparedStatement.setTimestamp(4, gender.getUpdatedAt());
            preparedStatement.setLong(5, gender.getUpdatedUserId());
            preparedStatement.setInt(6, gender.getId());
            preparedStatement.executeUpdate();
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Gender delete(Gender gender) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            gender.setActive(false);
            preparedStatement = connect.prepareStatement("update genders set active = ? where id = ?");
            preparedStatement.setBoolean(1, gender.getActive());
            preparedStatement.setLong(2, gender.getId());
            preparedStatement.executeUpdate();
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Gender> genders) throws Exception {
        if (genders.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Gender gender : genders) {
            set.add("" + gender.getCreatedUserId());
            set.add("" + gender.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Gender gender : genders) {
            for (User user : users) {
                if (gender.getCreatedUserId() == user.getId())
                    gender.setCreatedUser(user);
                if (gender.getUpdatedUserId() == user.getId())
                    gender.setUpdatedUser(user);
            }
        }
    }
}
