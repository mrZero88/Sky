package com.models;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class Task {

    private LongProperty id;
    private StringProperty title;
    private StringProperty description;
    private ObjectProperty<Timestamp> start;
    private ObjectProperty<Timestamp> end;
    private LongProperty issueId;
    private IntegerProperty stateId;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;
    private ObjectProperty<Issue> issue;
    private ObjectProperty<TaskState> state;

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

    public Timestamp getStart() {
        return start.get();
    }

    public void setStart(Timestamp start) {
        this.start.set(start);
    }

    public ObjectProperty<Timestamp> startProperty() {
        return start;
    }

    public Timestamp getEnd() {
        return end.get();
    }

    public void setEnd(Timestamp end) {
        this.end.set(end);
    }

    public ObjectProperty<Timestamp> endProperty() {
        return end;
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

    public Issue getIssue() {
        return issue.get();
    }

    public void setIssue(Issue issue) {
        this.issue.set(issue);
    }

    public ObjectProperty<Issue> issueProperty() {
        return issue;
    }

    public TaskState getState() {
        return state.get();
    }

    public void setState(TaskState state) {
        this.state.set(state);
    }

    public ObjectProperty<TaskState> stateProperty() {
        return state;
    }

    public Task() {
        this.id = new SimpleLongProperty(this, "id");
        this.title = new SimpleStringProperty(this, "title");
        this.description = new SimpleStringProperty(this, "description");
        this.start = new SimpleObjectProperty<>(this, "start");
        this.end = new SimpleObjectProperty<>(this, "end");
        this.issueId = new SimpleLongProperty(this, "issueId");
        this.stateId = new SimpleIntegerProperty(this, "stateId");
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
        this.issue = new SimpleObjectProperty<>(this, "issue");
        this.state = new SimpleObjectProperty<>(this, "state");
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

}
