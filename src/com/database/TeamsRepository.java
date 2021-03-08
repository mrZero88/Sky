package com.database;

import com.base.BaseRepository;
import com.models.Team;
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

public class TeamsRepository extends BaseRepository {

    public TeamsRepository() throws Exception {
        super();
    }

    public Team insert(Team team) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into teams " +
                            "(name, " +
                            "picture, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, team.getName());
            preparedStatement.setBinaryStream(2, team.getPicture());
            preparedStatement.setTimestamp(3, team.getCreatedAt());
            preparedStatement.setTimestamp(4, team.getUpdatedAt());
            preparedStatement.setLong(5, team.getCreatedUserId());
            preparedStatement.setLong(6, team.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from teams order by id desc limit 1");
                resultSet.next();
                team.setId(resultSet.getInt("id"));
            }
            return team;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Team getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Team team = new Team();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from teams where id=" + id);
            resultSet.next();
            team.setId(resultSet.getInt("id"));
            team.setName(resultSet.getString("name"));
            team.setPicture(resultSet.getBinaryStream("picture"));
            team.setCreatedAt(resultSet.getTimestamp("created_at"));
            team.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            team.setCreatedUserId(resultSet.getLong("created_user_id"));
            team.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            team.setActive(resultSet.getBoolean("active"));
            return team;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Team> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Team> teams = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from teams where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                team.setPicture(resultSet.getBinaryStream("picture"));
                team.setCreatedAt(resultSet.getTimestamp("created_at"));
                team.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                team.setCreatedUserId(resultSet.getLong("created_user_id"));
                team.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                team.setActive(resultSet.getBoolean("active"));
                teams.add(team);
            }
            return FXCollections.observableList(teams);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Team> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Team> teams = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from teams where active=true");
            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                team.setPicture(resultSet.getBinaryStream("picture"));
                team.setCreatedAt(resultSet.getTimestamp("created_at"));
                team.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                team.setCreatedUserId(resultSet.getLong("created_user_id"));
                team.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                team.setActive(resultSet.getBoolean("active"));
                teams.add(team);
            }
            return FXCollections.observableList(teams);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Team update(Team team) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            team.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update teams set name = ?," +
                            " picture = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, team.getName());
            preparedStatement.setBinaryStream(2, team.getPicture());
            preparedStatement.setTimestamp(3, team.getUpdatedAt());
            preparedStatement.setLong(4, team.getUpdatedUserId());
            preparedStatement.setLong(5, team.getId());
            preparedStatement.executeUpdate();
            return team;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Team delete(Team team) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            team.setActive(false);
            preparedStatement = connect.prepareStatement("update teams set active = ? where id = ?");
            preparedStatement.setBoolean(1, team.getActive());
            preparedStatement.setLong(2, team.getId());
            preparedStatement.executeUpdate();
            return team;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Team> teams) throws Exception {
        if (teams.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Team team : teams) {
            set.add("" + team.getCreatedUserId());
            set.add("" + team.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Team team : teams) {
            for (User user : users) {
                if (team.getCreatedUserId() == user.getId())
                    team.setCreatedUser(user);
                if (team.getUpdatedUserId() == user.getId())
                    team.setUpdatedUser(user);
            }
        }
    }
}
