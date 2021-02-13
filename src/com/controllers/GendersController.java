package com.controllers;

import com.database.GendersRepository;
import com.database.ProjectStatesRepository;
import com.models.FeatureState;
import com.models.Gender;
import com.models.ImageTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;

public class GendersController {

    @FXML
    private TableView gendersTable;

    public GendersController() {

    }

    @FXML
    public void initialize() {
        try {
            GendersRepository gendersRepository = new GendersRepository();

            /*int i = 1;
            File dir = new File("/Users/danielcorreia/IdeaProjects/Flyzerosky/src/com/images/Genders/");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (var child : Arrays.stream(directoryListing).sorted().toArray()) {
                    File file = (File) child;

                    if(file.getName().equals(".DS_Store"))
                        continue;

                    FileInputStream fis = new FileInputStream(file);

                    Gender g = gendersRepository.getById(i);
                    g.setGender(fis);
                    gendersRepository.update(g);
                    i++;
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }*/

            ObservableList<Gender> genders = gendersRepository.getAll();
            gendersRepository.loadUsers(genders);
            gendersTable.setItems(genders);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Gender, Integer> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Gender, String> tableColumnName = new TableColumn<>("Name");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Gender, String> tableColumnShortcut = new TableColumn<>("Shortcut");
        tableColumnShortcut.setCellValueFactory(new PropertyValueFactory<>("shortcut"));

        TableColumn<Gender, InputStream> tableColumnGender = new TableColumn<>("Gender");
        tableColumnGender.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGender()));
        tableColumnGender.setCellFactory(param -> new ImageTableCell());

        TableColumn<Gender, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Gender, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Gender, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<Gender, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        gendersTable.getColumns().addAll(tableColumnId, tableColumnName,
                tableColumnShortcut, tableColumnGender,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
