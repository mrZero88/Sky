package com.controllers;

import com.database.ProjectStatesRepository;
import com.database.TaskStatesRepository;
import com.models.ImageTableCell;
import com.models.ProjectState;
import com.models.TaskState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.sql.Timestamp;

public class TaskStatesController {

    @FXML
    private TableView taskStatesTable;

    public TaskStatesController() {

    }

    @FXML
    public void initialize() {
        try {
            TaskStatesRepository taskStatesRepository = new TaskStatesRepository();

            /*int i = 1;
            File dir = new File("/Users/danielcorreia/IdeaProjects/Flyzerosky/src/com/images/ProjectStates/");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (var child : Arrays.stream(directoryListing).sorted().toArray()) {
                    File file = (File) child;

                    if(file.getName().equals(".DS_Store"))
                        continue;

                    FileInputStream fis = new FileInputStream(file);

                    ProjectState ps = projectStatesRepository.getById(i);
                    ps.setState(fis);
                    projectStatesRepository.update(ps);
                    i++;
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }*/

            ObservableList<TaskState> taskStates = taskStatesRepository.getAll();
            taskStatesRepository.loadUsers(taskStates);
            taskStatesTable.setItems(taskStates);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<TaskState, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TaskState, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<TaskState, InputStream> tableColumnState = new TableColumn<>("State");
        tableColumnState.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getState()));
        tableColumnState.setCellFactory(param -> new ImageTableCell());

        TableColumn<TaskState, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<TaskState, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<TaskState, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<TaskState, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        taskStatesTable.getColumns().addAll(tableColumnId, tableColumnTitle, tableColumnState,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
