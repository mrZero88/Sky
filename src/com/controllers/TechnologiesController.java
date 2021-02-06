package com.controllers;

import com.database.TechnologiesRepository;
import com.models.Bug;
import com.models.Technology;
import com.models.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Timestamp;

public class TechnologiesController {

    @FXML
    private TableView technologiesTable;

    public TechnologiesController() {

    }

    @FXML
    public void initialize() {
        try {
            TechnologiesRepository technologiesRepository = new TechnologiesRepository();
            ObservableList<Technology> technologies = technologiesRepository.getAll();
            technologiesRepository.loadCreatedUsers(technologies);
            technologiesRepository.loadUpdatedUsers(technologies);
            technologiesTable.setItems(technologies);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Technology, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Technology, String> tableColumnName = new TableColumn<>("Name");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Technology, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Technology, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Technology, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<Technology, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        technologiesTable.getColumns().addAll(tableColumnId, tableColumnName,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
