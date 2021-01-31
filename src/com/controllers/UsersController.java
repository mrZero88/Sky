package com.controllers;

import com.database.UsersRepository;
import com.models.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

public class UsersController {

    @FXML
    private TableView usersTable;

    public UsersController() {

    }

    @FXML
    public void initialize() {
        try {
            UsersRepository usersRepository = new UsersRepository();
            ObservableList<User> users = usersRepository.getAll();
            usersRepository.loadUsers(users);
            usersRepository.loadCountries(users);
            usersTable.setItems(users);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<User, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, ImageView> tableColumnPicture = new TableColumn<>("Picture");
        tableColumnPicture.setCellValueFactory(new PropertyValueFactory<>("pictureView"));

        TableColumn<User, String> tableColumnFirstName = new TableColumn<>("First Name");
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> tableColumnLastName = new TableColumn<>("Last Name");
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> tableColumnUsername = new TableColumn<>("Username");
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<User, String> tableColumnEmail = new TableColumn<>("Email");
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Date> tableColumnBirthDate= new TableColumn<>("Birth Date");
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<User, String> tableColumnPhoneNumber = new TableColumn<>("Phone Number");
        tableColumnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<User, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<User, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<User, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<User, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        TableColumn<User, Long> tableColumnCountryName = new TableColumn<>("Country");
        tableColumnCountryName.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<User, ImageView> tableColumnCountry = new TableColumn<>("Country");
        tableColumnCountry.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCountry().getFlagView()));

        usersTable.getColumns().addAll(tableColumnId, tableColumnPicture, tableColumnFirstName, tableColumnLastName,
                tableColumnUsername, tableColumnEmail, tableColumnBirthDate, tableColumnPhoneNumber,
                tableColumnCreatedAt, tableColumnUpdatedAt, tableColumnCreatedUserId, tableColumnUpdatedUserId,
                tableColumnCountryName,
                tableColumnCountry);
    }

}
