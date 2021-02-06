package com.controllers;

import com.database.FeaturesRepository;
import com.models.Feature;
import com.models.Technology;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Timestamp;

public class FeaturesController {

    @FXML
    private TableView featuresTable;

    public FeaturesController() {

    }

    @FXML
    public void initialize() {
        try {
            FeaturesRepository featuresRepository = new FeaturesRepository();
            ObservableList<Feature> features = featuresRepository.getAll();
            featuresRepository.loadCreatedUsers(features);
            featuresRepository.loadUpdatedUsers(features);
            featuresRepository.loadFeatureStates(features);
            featuresTable.setItems(features);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Feature, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Feature, ImageView> tableColumnState = new TableColumn<>("State");
        tableColumnState.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getState().getStateView()));

        TableColumn<Feature, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Feature, Long> tableColumnProjectId = new TableColumn<>("Project Id");
        tableColumnProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));

        TableColumn<Feature, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Feature, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Feature, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<Feature, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        featuresTable.getColumns().addAll(tableColumnId, tableColumnState, tableColumnTitle,
                tableColumnProjectId, tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
