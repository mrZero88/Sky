package com.models;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.Instant;

public class Issue {

    private LongProperty id;
    private StringProperty title;
    private StringProperty description;
    private LongProperty projectId;
    private IntegerProperty typeId;
    private IntegerProperty stateId;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;
    private ObjectProperty<IssueState> state;
    private ObjectProperty<Project> project;
    private ObjectProperty<IssueType> type;

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

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
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

    public int getTypeId() {
        return typeId.get();
    }

    public void setTypeId(int typeId) {
        this.typeId.set(typeId);
    }

    public IntegerProperty typeIdProperty() {
        return typeId;
    }

    public int getStateId() {
        return stateId.get();
    }

    public void setStateId(int stateId) {
        this.stateId.set(stateId);
    }

    public IntegerProperty stateIdProperty() {
        return stateId;
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

    public IssueState getState() {
        return state.get();
    }

    public void setState(IssueState state) {
        this.state.set(state);
    }

    public ObjectProperty<IssueState> stateProperty() {
        return state;
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

    public IssueType getType() {
        return type.get();
    }

    public void setType(IssueType issueType) {
        this.type.set(issueType);
    }

    public ObjectProperty<IssueType> typeProperty() {
        return type;
    }

    public Issue() {
        this.id = new SimpleLongProperty(this, "id");
        this.title = new SimpleStringProperty(this, "title");
        this.description = new SimpleStringProperty(this, "description");
        this.projectId = new SimpleLongProperty(this, "projectId");
        this.typeId = new SimpleIntegerProperty(this, "typeId");
        this.stateId = new SimpleIntegerProperty(this, "stateId", 1);
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "UpdatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
        this.state = new SimpleObjectProperty<>(this, "state");
        this.project = new SimpleObjectProperty<>(this, "project");
        this.type = new SimpleObjectProperty<>(this, "type");
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
