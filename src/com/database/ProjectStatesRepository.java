package com.database;

import com.models.Bug;
import com.models.Project;
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

    public void loadUsers(ObservableList<ProjectState> projectStates) throws Exception {
        if(projectStates.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (ProjectState projectState : projectStates) {
            set.add("" + projectState.getCreatedUserId());
            set.add("" + projectState.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (ProjectState projectState : projectStates) {
            for (User user : users) {
                if (projectState.getCreatedUserId() == user.getId())
                    projectState.setCreatedUser(user);
                if (projectState.getUpdatedUserId() == user.getId())
                    projectState.setUpdatedUser(user);
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
