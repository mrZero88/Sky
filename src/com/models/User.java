package com.models;

import javafx.beans.property.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class User {

    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty userName;
    private StringProperty email;
    private ObjectProperty<Timestamp> emailVerifiedAt;
    private StringProperty password;
    private IntegerProperty typeId;
    private BooleanProperty isSuperUser;
    private IntegerProperty countryId;
    private ObjectProperty<InputStream> picture;
    private ObjectProperty<Date> birthDate;
    private StringProperty adress;
    private StringProperty postalCode;
    private StringProperty phoneNumber;
    private StringProperty rememberToken;
    private IntegerProperty genderId;
    private ObjectProperty<Timestamp> createdAt;
    private ObjectProperty<Timestamp> updatedAt;
    private LongProperty createdUserId;
    private LongProperty updatedUserId;
    private BooleanProperty active;

    private ObjectProperty<User> createdUser;
    private ObjectProperty<User> updatedUser;

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUsername(String userName) {
        this.userName.set(userName);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public Timestamp getEmailVerifiedAt() {
        return emailVerifiedAt.get();
    }

    public void setEmailVerifiedAt(Timestamp emailVerifiedAt) {
        this.emailVerifiedAt.set(emailVerifiedAt);
    }

    public ObjectProperty<Timestamp> emailVerifiedAtProperty() {
        return emailVerifiedAt;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
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

    public boolean getIsSuperUser() {
        return isSuperUser.get();
    }

    public void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser.set(isSuperUser);
    }

    public BooleanProperty isSuperUserProperty() {
        return isSuperUser;
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

    public InputStream getPicture() {
        return picture.get();
    }

    public void setPicture(InputStream picture) {
        this.picture.set(picture);
    }

    public ObjectProperty<InputStream> pictureProperty() {
        return picture;
    }

    public Date getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate.set(birthDate);
    }

    public ObjectProperty<Date> birthDateProperty() {
        return birthDate;
    }

    public String getAdress() {
        return adress.get();
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public StringProperty adressProperty() {
        return adress;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getRememberToken() {
        return rememberToken.get();
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken.set(rememberToken);
    }

    public StringProperty rememberTokenProperty() {
        return rememberToken;
    }

    public int getGenderId() {
        return genderId.get();
    }

    public void setGenderId(int genderId) {
        this.genderId.set(genderId);
    }

    public IntegerProperty genderIdProperty() {
        return genderId;
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

    public User() {
        this.id = new SimpleLongProperty(this, "id");
        this.firstName = new SimpleStringProperty(this, "firstName");
        this.lastName = new SimpleStringProperty(this, "lastName");
        this.userName = new SimpleStringProperty(this, "userName");
        this.email = new SimpleStringProperty(this, "email");
        this.emailVerifiedAt = new SimpleObjectProperty<>(this, "emailVerifiedAt");
        this.password = new SimpleStringProperty(this, "password");
        this.typeId = new SimpleIntegerProperty(this, "typeId", 1);
        this.isSuperUser = new SimpleBooleanProperty(this, "isSuperUser",false);
        this.countryId = new SimpleIntegerProperty(this, "countryId",1);
        this.picture = new SimpleObjectProperty<>(this, "picture");
        this.birthDate = new SimpleObjectProperty<>(this, "birthDate");
        this.adress = new SimpleStringProperty(this, "adress");
        this.postalCode = new SimpleStringProperty(this, "postalCode");
        this.phoneNumber = new SimpleStringProperty(this, "phoneNumber");
        this.rememberToken = new SimpleStringProperty(this, "rememberToken");
        this.genderId = new SimpleIntegerProperty(this, "genderId",1);
        this.createdAt = new SimpleObjectProperty<>(this, "createdAt", Timestamp.from(Instant.now()));
        this.updatedAt = new SimpleObjectProperty<>(this, "updatedAt", Timestamp.from(Instant.now()));
        this.createdUserId = new SimpleLongProperty(this, "createdUserId", 1);
        this.updatedUserId = new SimpleLongProperty(this, "updatedUserId", 1);
        this.active = new SimpleBooleanProperty(this, "active",true);
        this.createdUser = new SimpleObjectProperty<>(this, "createdUser");
        this.updatedUser = new SimpleObjectProperty<>(this, "updatedUser");
    }

    @Override
    public String toString() {
        return this.firstName.get() + " " + this.lastName.get();
    }
}
