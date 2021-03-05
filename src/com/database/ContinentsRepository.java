package com.database;

import com.models.BugState;
import com.models.Continent;
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

public class ContinentsRepository extends BaseRepository {

    public ContinentsRepository() throws Exception {
        super();
    }

    public Continent insert(Continent continent) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into continents " +
                            "(name, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, continent.getName());
            preparedStatement.setTimestamp(2, continent.getCreatedAt());
            preparedStatement.setTimestamp(3, continent.getUpdatedAt());
            preparedStatement.setLong(4, continent.getCreatedUserId());
            preparedStatement.setLong(5, continent.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from continents order by id desc limit 1");
                resultSet.next();
                continent.setId(resultSet.getInt("id"));
            }
            return continent;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Continent getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Continent continent = new Continent();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from continents where id=" + id);
            resultSet.next();
            continent.setId(resultSet.getInt("id"));
            continent.setName(resultSet.getString("name"));
            continent.setCreatedAt(resultSet.getTimestamp("created_at"));
            continent.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            continent.setCreatedUserId(resultSet.getLong("created_user_id"));
            continent.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            continent.setActive(resultSet.getBoolean("active"));
            return continent;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Continent> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Continent> continents = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from continents where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Continent continent = new Continent();
                continent.setId(resultSet.getInt("id"));
                continent.setName(resultSet.getString("name"));
                continent.setCreatedAt(resultSet.getTimestamp("created_at"));
                continent.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                continent.setCreatedUserId(resultSet.getLong("created_user_id"));
                continent.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                continent.setActive(resultSet.getBoolean("active"));
                continents.add(continent);
            }
            return FXCollections.observableList(continents);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Continent> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Continent> continents = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from continents where active=true");
            while (resultSet.next()) {
                Continent continent = new Continent();
                continent.setId(resultSet.getInt("id"));
                continent.setName(resultSet.getString("name"));
                continent.setCreatedAt(resultSet.getTimestamp("created_at"));
                continent.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                continent.setCreatedUserId(resultSet.getLong("created_user_id"));
                continent.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                continent.setActive(resultSet.getBoolean("active"));
                continents.add(continent);
            }
            return FXCollections.observableList(continents);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Continent update(Continent continent) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            continent.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update continents set name = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, continent.getName());
            preparedStatement.setTimestamp(2, continent.getUpdatedAt());
            preparedStatement.setLong(3, continent.getUpdatedUserId());
            preparedStatement.setLong(4, continent.getId());
            preparedStatement.executeUpdate();
            return continent;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Continent delete(Continent continent) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            continent.setActive(false);
            preparedStatement = connect.prepareStatement("update continents set active = ? where id = ?");
            preparedStatement.setBoolean(1, continent.getActive());
            preparedStatement.setLong(2, continent.getId());
            preparedStatement.executeUpdate();
            return continent;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Continent> continents) throws Exception {
        if (continents.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Continent continent : continents) {
            set.add("" + continent.getCreatedUserId());
            set.add("" + continent.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Continent continent : continents) {
            for (User user : users) {
                if (continent.getCreatedUserId() == user.getId())
                    continent.setCreatedUser(user);
                if (continent.getUpdatedUserId() == user.getId())
                    continent.setUpdatedUser(user);
            }
        }
    }
}
