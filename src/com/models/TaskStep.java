package com.models;

import javafx.beans.property.*;
import java.sql.Timestamp;
import java.time.Instant;

public class TaskStep {

    private LongProperty id;
    private StringProperty description;
    private IntegerProperty order;
    private LongProperty taskId;
    private BooleanProperty isDone;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;
    private ObjectProperty<Task> task;

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
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

    public int getOrder() {
        return order.get();
    }

    public void setOrder(int order) {
        this.order.set(order);
    }

    public IntegerProperty orderProperty() {
        return order;
    }

    public long getTaskId() {
        return taskId.get();
    }

    public void setTaskId(long taskId) {
        this.taskId.set(taskId);
    }

    public LongProperty taskIdProperty() {
        return taskId;
    }

    public boolean getIsDone() {
        return isDone.get();
    }

    public void setIsDone(boolean isDone) {
        this.isDone.set(isDone);
    }

    public BooleanProperty isDoneProperty() {
        return isDone;
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

    public Task getTask() {
        return task.get();
    }

    public void setTask(Task task) {
        this.task.set(task);
    }

    public ObjectProperty<Task> taskProperty() {
        return task;
    }

    public TaskStep() {
        this.id = new SimpleLongProperty(this, "id");
        this.description = new SimpleStringProperty(this, "description");
        this.order = new SimpleIntegerProperty(this, "order");
        this.taskId = new SimpleLongProperty(this, "taskId");
        this.isDone = new SimpleBooleanProperty(this, "isDone");
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
        this.task = new SimpleObjectProperty<>(this, "task");
    }

    @Override
    public String toString() {
        return this.getDescription();
    }

}
