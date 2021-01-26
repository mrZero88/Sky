package com.database;

import com.models.Bug;
import com.models.ProjectState;
import com.models.Technology;
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

public class TechnologiesRepository extends BaseRepository {

    public TechnologiesRepository() throws Exception {
        super();
    }

    public Technology insert(Technology technology) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into technologies " +
                            "(name, " +
                            "description, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, technology.getName());
            preparedStatement.setString(2, technology.getDescription());
            preparedStatement.setTimestamp(3, technology.getCreatedAt());
            preparedStatement.setTimestamp(4, technology.getUpdatedAt());
            preparedStatement.setLong(5, technology.getCreatedUserId());
            preparedStatement.setLong(6, technology.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from technologies order by id desc limit 1");
                resultSet.next();
                technology.setId(resultSet.getInt("id"));
            }
            return technology;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Technology getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Technology technology = new Technology();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from technologies where id=" + id);
            resultSet.next();
            technology.setId(resultSet.getLong("id"));
            technology.setName(resultSet.getString("name"));
            technology.setDescription(resultSet.getString("description"));
            technology.setCreatedAt(resultSet.getTimestamp("created_at"));
            technology.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            technology.setCreatedUserId(resultSet.getLong("created_user_id"));
            technology.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            technology.setActive(resultSet.getBoolean("active"));
            return technology;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Technology> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Technology> technologies = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from technologies where active=true");
            while (resultSet.next()) {
                Technology technology = new Technology();
                technology.setId(resultSet.getLong("id"));
                technology.setName(resultSet.getString("name"));
                technology.setDescription(resultSet.getString("description"));
                technology.setCreatedAt(resultSet.getTimestamp("created_at"));
                technology.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                technology.setCreatedUserId(resultSet.getLong("created_user_id"));
                technology.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                technology.setActive(resultSet.getBoolean("active"));
                technologies.add(technology);
            }
            return FXCollections.observableList(technologies);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Technology update(Technology technology) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            technology.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update technologies set name = ?," +
                            " description = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, technology.getName());
            preparedStatement.setString(2, technology.getDescription());
            preparedStatement.setTimestamp(3, technology.getUpdatedAt());
            preparedStatement.setLong(4, technology.getUpdatedUserId());
            preparedStatement.setLong(5, technology.getId());
            preparedStatement.executeUpdate();
            return technology;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Technology delete(Technology technology) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            technology.setActive(false);
            preparedStatement = connect.prepareStatement("update technologies set active = ? where id = ?");
            preparedStatement.setBoolean(1, technology.getActive());
            preparedStatement.setLong(2, technology.getId());
            preparedStatement.executeUpdate();
            return technology;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Technology> technologies) throws Exception {
        if(technologies.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Technology technology : technologies) {
            set.add("" + technology.getCreatedUserId());
            set.add("" + technology.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Technology technology : technologies) {
            for (User user : users) {
                if (technology.getCreatedUserId() == user.getId())
                    technology.setCreatedUser(user);
                if (technology.getUpdatedUserId() == user.getId())
                    technology.setUpdatedUser(user);
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
