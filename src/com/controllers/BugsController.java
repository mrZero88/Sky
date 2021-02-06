package com.controllers;

import com.database.BugsRepository;
import com.models.Bug;
import com.models.Technology;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Timestamp;

public class BugsController {

    @FXML
    private TableView bugsTable;

    public BugsController() {

    }

    @FXML
    public void initialize() {
        try {
            BugsRepository bugsRepository = new BugsRepository();
            ObservableList<Bug> bugs = bugsRepository.getAll();
            bugsRepository.loadCreatedUsers(bugs);
            bugsRepository.loadUpdatedUsers(bugs);
            bugsRepository.loadBugStates(bugs);
            bugsTable.setItems(bugs);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Bug, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bug, ImageView> tableColumnState = new TableColumn<>("State View");
        tableColumnState.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getState().getStateView()));

        TableColumn<Bug, String> tableColumnStateDescription = new TableColumn<>("State");
        tableColumnStateDescription.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getState().getTitle()));

        TableColumn<Bug, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Bug, Long> tableColumnProjectId = new TableColumn<>("Project Id");
        tableColumnProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));

        TableColumn<Bug, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Bug, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Bug, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<Bug, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        bugsTable.getColumns().addAll(tableColumnId, tableColumnState, tableColumnStateDescription, tableColumnTitle,
                tableColumnProjectId, tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
