package com.models;

import javafx.beans.property.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class Sprint {

    private LongProperty id;
    private StringProperty title;
    private ObjectProperty<Date> start;
    private ObjectProperty<Date> end;
    private LongProperty projectId;
    private LongProperty teamId;
    private StringProperty notes;
    private LongProperty previousSprintId;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;
    private ObjectProperty<Project> project;
    private ObjectProperty<Team> team;
    private ObjectProperty<Sprint> previousSprint;

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public Date getStart() {
        return start.get();
    }

    public void setStart(Date start) {
        this.start.set(start);
    }

    public ObjectProperty<Date> startProperty() {
        return start;
    }

    public Date getEnd() {
        return end.get();
    }

    public void setEnd(Date end) {
        this.end.set(end);
    }

    public ObjectProperty<Date> endProperty() {
        return end;
    }

    public long getProjectId() {
        return projectId.get();
    }

    public void setProjectId(long projectId) {
        this.projectId.set(projectId);
    }

    public LongProperty projectIdProperty() {
        return projectId;
    }

    public long getTeamId() {
        return teamId.get();
    }

    public void setTeamId(long teamId) {
        this.teamId.set(teamId);
    }

    public LongProperty teamIdProperty() {
        return teamId;
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public long getPreviousSprintId() {
        return previousSprintId.get();
    }

    public void setPreviousSprintId(long previousSprintId) {
        this.previousSprintId.set(previousSprintId);
    }

    public LongProperty previousSprintIdProperty() {
        return previousSprintId;
    }

    public Timestamp getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt.set(createdAt);
    }

    public ObjectProperty<Timestamp> createdAtProperty() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt.get();
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt.set(updatedAt);
    }

    public ObjectProperty<Timestamp> updatedAtProperty() {
        return updatedAt;
    }

    public long getCreatedUserId() {
        return createdUserId.get();
    }

    public void setCreatedUserId(long createdUserId) {
        this.createdUserId.set(createdUserId);
    }

    public LongProperty createdUserIdProperty() {
        return createdUserId;
    }

    public long getUpdatedUserId() {
        return updatedUserId.get();
    }

    public void setUpdatedUserId(long updatedUserId) {
        this.updatedUserId.set(updatedUserId);
    }

    public LongProperty updatedUserIdProperty() {
        return updatedUserId;
    }

    public boolean getActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public User getCreatedUser() {
        return createdUser.get();
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser.set(createdUser);
    }

    public ObjectProperty<User> createdUserProperty() {
        return createdUser;
    }

    public User getUpdatedUser() {
        return updatedUser.get();
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser.set(updatedUser);
    }

    public ObjectProperty<User> updatedUserProperty() {
        return updatedUser;
    }

    public Project getProject() {
        return project.get();
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public Team getTeam() {
        return team.get();
    }

    public void setTeam(Team team) {
        this.team.set(team);
    }

    public ObjectProperty<Team> teamProperty() {
        return team;
    }

    public Sprint getPreviousSprint() {
        return previousSprint.get();
    }

    public void setPreviousSprint(Sprint previousSprint) {
        this.previousSprint.set(previousSprint);
    }

    public ObjectProperty<Sprint> previousSprintProperty() {
        return previousSprint;
    }

    public Sprint() {
        this.id = new SimpleLongProperty(this, "id");
        this.title = new SimpleStringProperty(this, "title");
        this.start = new SimpleObjectProperty<>(this, "start");
        this.end = new SimpleObjectProperty<>(this, "end");
        this.projectId = new SimpleLongProperty(this, "projectId");
        this.teamId = new SimpleLongProperty(this, "teamId");
        this.notes = new SimpleStringProperty(this, "notes");
        this.previousSprintId = new SimpleLongProperty(this, "previousSprintId");
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
        this.project = new SimpleObjectProperty<>(this, "project");
        this.team = new SimpleObjectProperty<>(this, "team");
        this.previousSprint = new SimpleObjectProperty<>(this, "previousSprint");
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

}
