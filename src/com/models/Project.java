package com.models;

import javafx.beans.property.*;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class Project {

    private LongProperty id;
    private StringProperty title;
    private StringProperty description;
    private ObjectProperty<Date> start;
    private ObjectProperty<Date> end;
    private DoubleProperty totalCost;
    private IntegerProperty currencyId;
    private LongProperty customerId;
    private IntegerProperty stateId;
    private ObjectProperty<InputStream> picture;
    private IntegerProperty countryId;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;
    private ObjectProperty<Country> country;
    private ObjectProperty<Currency> currency;
    private ObjectProperty<Customer> customer;
    private ObjectProperty<ProjectState> state;

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

    public double getTotalCost() {
        return totalCost.get();
    }

    public void setTotalCost(double totalCost) {
        this.totalCost.set(totalCost);
    }

    public DoubleProperty totalCostProperty() {
        return totalCost;
    }

    public int getCurrencyId() {
        return currencyId.get();
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId.set(currencyId);
    }

    public IntegerProperty currencyIdProperty() {
        return currencyId;
    }

    public long getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(long customerId) {
        this.customerId.set(customerId);
    }

    public LongProperty customerIdProperty() {
        return customerId;
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

    public InputStream getPicture() {
        return picture.get();
    }

    public void setPicture(InputStream picture) {
        this.picture.set(picture);
    }

    public ObjectProperty<InputStream> pictureProperty() {
        return picture;
    }

    public int getCountryId() {
        return countryId.get();
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public IntegerProperty countryIdProperty() {
        return countryId;
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

    public Country getCountry() {
        return country.get();
    }

    public void setCountry(Country country) {
        this.country.set(country);
    }

    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    public Currency getCurrency() {
        return currency.get();
    }

    public void setCurrency(Currency currency) {
        this.currency.set(currency);
    }

    public ObjectProperty<Currency> currencyProperty() {
        return currency;
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    public ProjectState getState() {
        return state.get();
    }

    public void setState(ProjectState state) {
        this.state.set(state);
    }

    public ObjectProperty<ProjectState> stateProperty() {
        return state;
    }

    public Project() {
        this.id = new SimpleLongProperty(this, "id");
        this.title = new SimpleStringProperty(this, "title");
        this.description = new SimpleStringProperty(this, "description");
        this.start = new SimpleObjectProperty<>(this, "start");
        this.end = new SimpleObjectProperty<>(this, "end");
        this.totalCost = new SimpleDoubleProperty(this, "totalCost");
        this.currencyId = new SimpleIntegerProperty(this, "currencyId");
        this.customerId = new SimpleLongProperty(this, "customerId");
        this.stateId = new SimpleIntegerProperty(this, "stateId");
        this.picture = new SimpleObjectProperty<>(this, "picture");
        this.countryId = new SimpleIntegerProperty(this, "countryId");
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active", true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
        this.country = new SimpleObjectProperty<>(this, "country");
        this.currency = new SimpleObjectProperty<>(this, "currency");
        this.customer = new SimpleObjectProperty<>(this, "customer");
        this.state = new SimpleObjectProperty<>(this, "state");
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

}
