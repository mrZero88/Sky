package com.database;

import com.base.BaseRepository;
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

public class SprintsRepository extends BaseRepository {

    public SprintsRepository() throws Exception {
        super();
    }

    public Sprint insert(Sprint sprint) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into sprints " +
                            "(title, " +
                            "start, " +
                            "end, " +
                            "project_id, " +
                            "team_id, " +
                            "notes, " +
                            "previous_sprint_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, sprint.getTitle());
            preparedStatement.setDate(2, sprint.getStart());
            preparedStatement.setDate(3, sprint.getEnd());
            preparedStatement.setLong(4, sprint.getProjectId());
            preparedStatement.setLong(5, sprint.getTeamId());
            preparedStatement.setString(6, sprint.getNotes());
            preparedStatement.setLong(7, sprint.getPreviousSprintId());
            preparedStatement.setTimestamp(8, sprint.getCreatedAt());
            preparedStatement.setTimestamp(9, sprint.getUpdatedAt());
            preparedStatement.setLong(10, sprint.getCreatedUserId());
            preparedStatement.setLong(11, sprint.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from sprints order by id desc limit 1");
                resultSet.next();
                sprint.setId(resultSet.getInt("id"));
            }
            return sprint;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Sprint getById(long id) throws Exception{
        try {
            connect = DriverManager.getConnection(CONN);
            Sprint sprint = new Sprint();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints where id=" + id);
            resultSet.next();
            sprint.setId(resultSet.getInt("id"));
            sprint.setTitle(resultSet.getString("title"));
            sprint.setStart(resultSet.getDate("start"));
            sprint.setEnd(resultSet.getDate("end"));
            sprint.setProjectId(resultSet.getLong("project_id"));
            sprint.setTeamId(resultSet.getLong("team_id"));
            sprint.setNotes(resultSet.getString("notes"));
            sprint.setPreviousSprintId(resultSet.getLong("previous_sprint_id"));
            sprint.setCreatedAt(resultSet.getTimestamp("created_at"));
            sprint.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            sprint.setCreatedUserId(resultSet.getLong("created_user_id"));
            sprint.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            sprint.setActive(resultSet.getBoolean("active"));
            return sprint;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Sprint> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Sprint> sprints = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from sprints where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Sprint sprint = new Sprint();
                sprint.setId(resultSet.getInt("id"));
                sprint.setTitle(resultSet.getString("title"));
                sprint.setStart(resultSet.getDate("start"));
                sprint.setEnd(resultSet.getDate("end"));
                sprint.setProjectId(resultSet.getLong("project_id"));
                sprint.setTeamId(resultSet.getLong("team_id"));
                sprint.setNotes(resultSet.getString("notes"));
                sprint.setPreviousSprintId(resultSet.getLong("previous_sprint_id"));
                sprint.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprint.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprint.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprint.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprint.setActive(resultSet.getBoolean("active"));
                sprints.add(sprint);
            }
            return FXCollections.observableList(sprints);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Sprint> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Sprint> sprints = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from sprints where active=true");
            while (resultSet.next()) {
                Sprint sprint = new Sprint();
                sprint.setId(resultSet.getInt("id"));
                sprint.setTitle(resultSet.getString("title"));
                sprint.setStart(resultSet.getDate("start"));
                sprint.setEnd(resultSet.getDate("end"));
                sprint.setProjectId(resultSet.getLong("project_id"));
                sprint.setTeamId(resultSet.getLong("team_id"));
                sprint.setNotes(resultSet.getString("notes"));
                sprint.setPreviousSprintId(resultSet.getLong("previous_sprint_id"));
                sprint.setCreatedAt(resultSet.getTimestamp("created_at"));
                sprint.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                sprint.setCreatedUserId(resultSet.getLong("created_user_id"));
                sprint.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                sprint.setActive(resultSet.getBoolean("active"));
                sprints.add(sprint);
            }
            return FXCollections.observableList(sprints);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Sprint update(Sprint sprint) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            sprint.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update sprints set" +
                            " title = ?," +
                            " start = ?," +
                            " end = ?," +
                            " project_id = ?," +
                            " team_id = ?," +
                            " notes = ?," +
                            " previous_sprint_id = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, sprint.getTitle());
            preparedStatement.setDate(2, sprint.getStart());
            preparedStatement.setDate(3, sprint.getEnd());
            preparedStatement.setLong(4, sprint.getProjectId());
            preparedStatement.setLong(5, sprint.getTeamId());
            preparedStatement.setString(6, sprint.getNotes());
            preparedStatement.setLong(7, sprint.getPreviousSprintId());
            preparedStatement.setTimestamp(8, sprint.getUpdatedAt());
            preparedStatement.setLong(9, sprint.getUpdatedUserId());
            preparedStatement.setLong(10, sprint.getId());
            preparedStatement.executeUpdate();
            return sprint;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Sprint delete(Sprint sprint) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            sprint.setActive(false);
            preparedStatement = connect.prepareStatement("update sprints set active = ? where id = ?");
            preparedStatement.setBoolean(1, sprint.getActive());
            preparedStatement.setLong(2, sprint.getId());
            preparedStatement.executeUpdate();
            return sprint;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Sprint> sprints) throws Exception {
        if (sprints.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Sprint sprint : sprints) {
            set.add("" + sprint.getCreatedUserId());
            set.add("" + sprint.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Sprint sprint : sprints) {
            for (User user : users) {
                if (sprint.getCreatedUserId() == user.getId())
                    sprint.setCreatedUser(user);
                if (sprint.getUpdatedUserId() == user.getId())
                    sprint.setUpdatedUser(user);
            }
        }
    }

    public void loadProjects(ObservableList<Sprint> sprints) throws Exception {
        if (sprints.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Sprint sprint : sprints) {
            set.add("" + sprint.getProjectId());
        }

        ProjectsRepository pr = new ProjectsRepository();
        ObservableList<Project> projects = pr.getByIds(set);

        for (Sprint sprint : sprints) {
            for (Project project : projects) {
                if (sprint.getProjectId() == project.getId())
                    sprint.setProject(project);
            }
        }
    }

    public void loadTeams(ObservableList<Sprint> sprints) throws Exception {
        if (sprints.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Sprint sprint : sprints) {
            set.add("" + sprint.getTeamId());
        }

        TeamsRepository tr = new TeamsRepository();
        ObservableList<Team> teams = tr.getByIds(set);

        for (Sprint sprint : sprints) {
            for (Team team : teams) {
                if (sprint.getTeamId() == team.getId())
                    sprint.setTeam(team);
            }
        }
    }

    public void loadPreviosSprints(ObservableList<Sprint> sprints) throws Exception {
        if (sprints.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Sprint sprint : sprints) {
            set.add("" + sprint.getPreviousSprintId());
        }

        SprintsRepository sr = new SprintsRepository();
        ObservableList<Sprint> previousSprints = sr.getByIds(set);

        for (Sprint sprint : sprints) {
            for (Sprint prevSprint : previousSprints) {
                if (sprint.getTeamId() == prevSprint.getId())
                    sprint.setPreviousSprint(prevSprint);
            }
        }
    }
}
