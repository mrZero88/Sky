package com.controllers;

import com.database.BugsRepository;
import com.database.ProjectStatesRepository;
import com.models.Bug;
import com.models.ProjectState;
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

public class ProjectStatesController {

    @FXML
    private TableView projectStatesTable;

    public ProjectStatesController() {

    }

    @FXML
    public void initialize() {
        try {
            ProjectStatesRepository projectStatesRepository = new ProjectStatesRepository();

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

            ObservableList<ProjectState> projectStates = projectStatesRepository.getAll();
            projectStatesRepository.loadCreatedUsers(projectStates);
            projectStatesRepository.loadUpdatedUsers(projectStates);
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

        TableColumn<ProjectState, ImageView> tableColumnState = new TableColumn<>("State");
        tableColumnState.setCellValueFactory(new PropertyValueFactory<>("stateView"));

        TableColumn<ProjectState, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<ProjectState, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<ProjectState, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<ProjectState, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        projectStatesTable.getColumns().addAll(tableColumnId, tableColumnTitle, tableColumnState,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
