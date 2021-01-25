package com.models;

import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;

public class Country {

    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<InputStream> flag;
    private ObjectProperty<ImageView> flagView;
    private StringProperty shortcut;
    private IntegerProperty continentId;
    private StringProperty fileName;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public InputStream getFlag() {
        return flag.get();
    }

    public void setFlag(InputStream flag) throws Exception {
        if (flag != null) {
            Image image = SwingFXUtils.toFXImage(ImageIO.read(flag), null);
            flagView.get().setImage(image);
        }
        this.flag.set(flag);
    }

    public ObjectProperty<InputStream> flagProperty() {
        return flag;
    }

    public ImageView getFlagView() {
        return flagView.get();
    }

    public void setFlagView(ImageView flagView) {
        this.flagView.set(flagView);
    }

    public ObjectProperty<ImageView> flagViewProperty() {
        return flagView;
    }

    public String getShortcut() {
        return shortcut.get();
    }

    public void setShortcut(String shortcut) {
        this.shortcut.set(shortcut);
    }

    public StringProperty smallVersionProperty() {
        return shortcut;
    }

    public int getContinentId() {
        return continentId.get();
    }

    public void setContinentId(int continentId) {
        this.continentId.set(continentId);
    }

    public IntegerProperty continentIdProperty() {
        return continentId;
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public StringProperty fileNameProperty() {
        return name;
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

    public Country() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.name = new SimpleStringProperty(this, "name");
        this.flag = new SimpleObjectProperty<>(this, "flag");
        this.flagView = new SimpleObjectProperty<>(this, "flagView", new ImageView());
        this.shortcut = new SimpleStringProperty(this, "shortcut");
        this.continentId = new SimpleIntegerProperty(this, "continentId",7);
        this.fileName = new SimpleStringProperty(this, "fileName");
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
    }
}
