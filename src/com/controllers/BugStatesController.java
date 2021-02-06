package com.controllers;

import com.database.BugStatesRepository;
import com.models.BugState;
import com.models.Technology;
import javafx.beans.property.SimpleObjectProperty;
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

public class BugStatesController {

    @FXML
    private TableView bugStatesTable;

    public BugStatesController() {

    }

    @FXML
    public void initialize() {
        try {
            BugStatesRepository bugStatesRepository = new BugStatesRepository();

            /*int i = 1;
            File dir = new File("/Users/danielcorreia/IdeaProjects/Flyzerosky/src/com/images/States/");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (var child : Arrays.stream(directoryListing).sorted().toArray()) {
                    File file = (File) child;

                    if(file.getName().equals(".DS_Store"))
                        continue;

                    FileInputStream fis = new FileInputStream(file);

                    BugState bs = bugStatesRepository.getById(i);
                    bs.setState(fis);
                    bugStatesRepository.update(bs);
                    i++;
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }*/

            ObservableList<BugState> bugStates = bugStatesRepository.getAll();
            bugStatesRepository.loadCreatedUsers(bugStates);
            bugStatesRepository.loadUpdatedUsers(bugStates);
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

        TableColumn<BugState, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<BugState, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        bugStatesTable.getColumns().addAll(tableColumnId, tableColumnTitle, tableColumnState,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
