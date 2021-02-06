package com.controllers;

import com.database.GendersRepository;
import com.database.ProjectStatesRepository;
import com.models.Gender;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Arrays;

public class GendersController {

    @FXML
    private TableView gendersTable;

    public GendersController() {

    }

    @FXML
    public void initialize() {
        try {
            GendersRepository gendersRepository = new GendersRepository();

            ObservableList<Gender> genders = gendersRepository.getAll();
            gendersRepository.loadCreatedUsers(genders);
            gendersRepository.loadUpdatedUsers(genders);
            gendersTable.setItems(genders);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Gender, Integer> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Gender, String> tableColumnName = new TableColumn<>("Name");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Gender, String> tableColumnShortcut = new TableColumn<>("Shortcut");
        tableColumnShortcut.setCellValueFactory(new PropertyValueFactory<>("shortcut"));

        TableColumn<Gender, ImageView> tableColumnGender = new TableColumn<>("Gender");
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("genderView"));

        TableColumn<Gender, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Gender, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Gender, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<Gender, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        gendersTable.getColumns().addAll(tableColumnId, tableColumnName,
                tableColumnShortcut, tableColumnGender,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
