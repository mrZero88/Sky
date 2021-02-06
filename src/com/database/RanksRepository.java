package com.database;

import com.models.Rank;
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

public class RanksRepository extends BaseRepository {

    public RanksRepository() throws Exception {
        super();
    }

    public Rank insert(Rank rank) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into ranks " +
                            "(title, " +
                            "level, " +
                            "badge, " +
                            "tasks_required, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, rank.getTitle());
            preparedStatement.setInt(2, rank.getLevel());
            preparedStatement.setBinaryStream(3, rank.getBadge());
            preparedStatement.setInt(4, rank.getTasksRequired());
            preparedStatement.setTimestamp(5, rank.getCreatedAt());
            preparedStatement.setTimestamp(6, rank.getUpdatedAt());
            preparedStatement.setLong(7, rank.getCreatedUserId());
            preparedStatement.setLong(8, rank.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from ranks order by id desc limit 1");
                resultSet.next();
                rank.setId(resultSet.getInt("id"));
            }
            return rank;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Rank getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Rank rank = new Rank();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from ranks where id=" + id);
            resultSet.next();
            rank.setId(resultSet.getInt("id"));
            rank.setTitle(resultSet.getString("title"));
            rank.setLevel(resultSet.getInt("level"));
            rank.setBadge(resultSet.getBinaryStream("badge"));
            rank.setTasksRequired(resultSet.getInt("tasks_required"));
            rank.setCreatedAt(resultSet.getTimestamp("created_at"));
            rank.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            rank.setCreatedUserId(resultSet.getLong("created_user_id"));
            rank.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            rank.setActive(resultSet.getBoolean("active"));
            return rank;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Rank> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Rank> ranks = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from ranks where active=true");
            while (resultSet.next()) {
                Rank rank = new Rank();
                rank.setId(resultSet.getInt("id"));
                rank.setTitle(resultSet.getString("title"));
                rank.setLevel(resultSet.getInt("level"));
                rank.setBadge(resultSet.getBinaryStream("badge"));
                rank.setTasksRequired(resultSet.getInt("tasks_required"));
                rank.setCreatedAt(resultSet.getTimestamp("created_at"));
                rank.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                rank.setCreatedUserId(resultSet.getLong("created_user_id"));
                rank.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                rank.setActive(resultSet.getBoolean("active"));
                ranks.add(rank);
            }
            return FXCollections.observableList(ranks);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Rank update(Rank rank) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            rank.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update ranks set" +
                            " title = ?," +
                            " level = ?," +
                            " badge = ?," +
                            " tasks_required = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, rank.getTitle());
            preparedStatement.setInt(2, rank.getLevel());
            preparedStatement.setBinaryStream(3, rank.getBadge());
            preparedStatement.setInt(4, rank.getTasksRequired());
            preparedStatement.setTimestamp(5, rank.getUpdatedAt());
            preparedStatement.setLong(6, rank.getUpdatedUserId());
            preparedStatement.setInt(7, rank.getId());
            preparedStatement.executeUpdate();
            return rank;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Rank delete(Rank rank) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            rank.setActive(false);
            preparedStatement = connect.prepareStatement("update ranks set active = ? where id = ?");
            preparedStatement.setBoolean(1, rank.getActive());
            preparedStatement.setLong(2, rank.getId());
            preparedStatement.executeUpdate();
            return rank;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Rank> loadCreatedUsers(ObservableList<Rank> ranks) throws Exception {
        if (ranks.isEmpty())
            return ranks;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Rank rank : ranks) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + rank.getCreatedUserId());
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
                    rank.setCreatedUser(user);
                }
            }
            return ranks;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Rank> loadUpdatedUsers(ObservableList<Rank> ranks) throws Exception {
        if (ranks.isEmpty())
            return ranks;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Rank rank : ranks) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + rank.getUpdatedUserId());
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
                    rank.setUpdatedUser(user);
                }
            }
            return ranks;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
