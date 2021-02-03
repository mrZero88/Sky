package com.controllers;

import com.database.BugStatesRepository;
import com.models.BugState;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.sql.Timestamp;

public class BugStatesController {

    @FXML
    private TableView bugStatesTable;

    public BugStatesController() {

    }

    @FXML
    public void initialize() {
        try {
            BugStatesRepository bugStatesRepository = new BugStatesRepository();
            ObservableList<BugState> bugStates = bugStatesRepository.getAll();
            bugStatesRepository.loadUsers(bugStates);
            bugStatesTable.setItems(bugStates);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<BugState, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BugState, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BugState, ImageView> tableColumnState = new TableColumn<>("State");
        tableColumnState.setCellValueFactory(new PropertyValueFactory<>("stateView"));

        TableColumn<BugState, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<BugState, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<BugState, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<BugState, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        bugStatesTable.getColumns().addAll(tableColumnId, tableColumnTitle, tableColumnState,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
