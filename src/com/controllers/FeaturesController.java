package com.controllers;

import com.database.BugsRepository;
import com.database.FeaturesRepository;
import com.models.Bug;
import com.models.Feature;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
            featuresRepository.loadUsers(features);
            featuresTable.setItems(features);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Feature, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Feature, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Feature, Long> tableColumnProjectId = new TableColumn<>("Project Id");
        tableColumnProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));

        TableColumn<Feature, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Feature, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Feature, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<Feature, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        featuresTable.getColumns().addAll(tableColumnId, tableColumnTitle,
                tableColumnProjectId, tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
