package com.controllers;

import com.database.BugStatesRepository;
import com.database.FeatureStatesRepository;
import com.models.BugState;
import com.models.FeatureState;
import com.models.ImageTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.sql.Timestamp;

public class FeatureStatesController {

    @FXML
    private TableView featureStatesTable;

    public FeatureStatesController() {

    }

    @FXML
    public void initialize() {
        try {
            FeatureStatesRepository featureStatesRepository = new FeatureStatesRepository();
            ObservableList<FeatureState> featureStates = featureStatesRepository.getAll();
            featureStatesRepository.loadUsers(featureStates);
            featureStatesTable.setItems(featureStates);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<FeatureState, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<FeatureState, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<FeatureState, InputStream> tableColumnState = new TableColumn<>("State");
        tableColumnState.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getState()));
        tableColumnState.setCellFactory(param -> new ImageTableCell());

        TableColumn<FeatureState, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<FeatureState, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<FeatureState, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<FeatureState, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        featureStatesTable.getColumns().addAll(tableColumnId, tableColumnTitle, tableColumnState,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
