package com.controllers;

import com.database.BugsRepository;
import com.database.ProjectStatesRepository;
import com.models.Bug;
import com.models.ProjectState;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class ProjectStatesController {

    @FXML
    private TableView projectStatesTable;

    public ProjectStatesController() {

    }

    @FXML
    public void initialize() {
        try {
            ProjectStatesRepository projectStatesRepository = new ProjectStatesRepository();
            ObservableList<ProjectState> projectStates = projectStatesRepository.getAll();
            projectStatesRepository.loadUsers(projectStates);
            projectStatesTable.setItems(projectStates);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<ProjectState, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProjectState, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<ProjectState, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<ProjectState, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<ProjectState, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<ProjectState, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        projectStatesTable.getColumns().addAll(tableColumnId, tableColumnTitle,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
