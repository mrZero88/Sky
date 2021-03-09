package com.models;

import javafx.beans.property.*;
import java.sql.Timestamp;
import java.time.Instant;

public class SprintIssue {

    private LongProperty id;
    private LongProperty issueId;
    private LongProperty sprintId;
    private BooleanProperty isClosed;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;
    private ObjectProperty<Issue> issue;
    private ObjectProperty<Sprint> sprint;

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public long getIssueId() {
        return issueId.get();
    }

    public void setIssueId(long issueId) {
        this.issueId.set(issueId);
    }

    public LongProperty issueIdProperty() {
        return issueId;
    }

    public long getSprintId() {
        return sprintId.get();
    }

    public void setSprintId(long sprintId) {
        this.sprintId.set(sprintId);
    }

    public LongProperty sprintIdProperty() {
        return sprintId;
    }

    public boolean getIsClosed() {
        return isClosed.get();
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed.set(isClosed);
    }

    public BooleanProperty isClosedProperty() {
        return isClosed;
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

    public Issue getIssue() {
        return issue.get();
    }

    public void setIssue(Issue issue) {
        this.issue.set(issue);
    }

    public ObjectProperty<Issue> issueProperty() {
        return issue;
    }

    public Sprint getSprint() {
        return sprint.get();
    }

    public void setSprint(Sprint sprint) {
        this.sprint.set(sprint);
    }

    public ObjectProperty<Sprint> stateProperty() {
        return sprint;
    }

    public SprintIssue() {
        this.id = new SimpleLongProperty(this, "id");
        this.issueId = new SimpleLongProperty(this, "issueId");
        this.sprintId = new SimpleLongProperty(this, "sprintId");
        this.isClosed = new SimpleBooleanProperty(this, "isClosed");
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
        this.issue = new SimpleObjectProperty<>(this, "issue");
        this.sprint = new SimpleObjectProperty<>(this, "sprint");
    }

}
