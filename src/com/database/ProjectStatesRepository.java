package com.database;

import com.models.Bug;
import com.models.ProjectState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, projectState.getTitle());
            preparedStatement.setTimestamp(2, projectState.getCreatedAt());
            preparedStatement.setTimestamp(3, projectState.getUpdatedAt());
            preparedStatement.setLong(4, projectState.getCreatedUserId());
            preparedStatement.setLong(5, projectState.getUpdatedUserId());
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
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, projectState.getTitle());
            preparedStatement.setTimestamp(2, projectState.getUpdatedAt());
            preparedStatement.setLong(3, projectState.getUpdatedUserId());
            preparedStatement.setLong(4, projectState.getId());
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

    protected void closeConnections() throws Exception {
        if (this.preparedStatement != null)
            this.preparedStatement.close();
        if (this.statement != null)
            this.statement.close();
        if (this.resultSet != null)
            this.resultSet.close();
    }
}
