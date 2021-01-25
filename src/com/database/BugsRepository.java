package com.database;

import com.models.Bug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, bug.getTitle());
            preparedStatement.setString(2, bug.getDescription());
            preparedStatement.setLong(3, bug.getProjectId());
            preparedStatement.setTimestamp(4, bug.getCreatedAt());
            preparedStatement.setTimestamp(5, bug.getUpdatedAt());
            preparedStatement.setLong(6, bug.getCreatedUserId());
            preparedStatement.setLong(7, bug.getUpdatedUserId());
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
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, bug.getTitle());
            preparedStatement.setString(2, bug.getDescription());
            preparedStatement.setLong(3, bug.getProjectId());
            preparedStatement.setTimestamp(4, bug.getUpdatedAt());
            preparedStatement.setLong(5, bug.getUpdatedUserId());
            preparedStatement.setLong(6, bug.getId());
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

    protected void closeConnections() throws Exception {
        if (this.preparedStatement != null)
            this.preparedStatement.close();
        if (this.statement != null)
            this.statement.close();
        if (this.resultSet != null)
            this.resultSet.close();
    }
}
