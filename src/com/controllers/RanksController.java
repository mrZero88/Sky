package com.controllers;

import com.database.RanksRepository;
import com.models.Rank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RanksController {

    @FXML
    private TableView ranksTable;

    public RanksController() {

    }

    @FXML
    public void initialize() {
        try {
            RanksRepository ranksRepository = new RanksRepository();

            /*List<Rank> ranks = new ArrayList<>();
            File dir = new File("/Users/danielcorreia/IdeaProjects/Flyzerosky/src/com/images/RankIcons/");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (var child : Arrays.stream(directoryListing).sorted().toArray()) {
                    File file = (File) child;

                    if(file.getName().equals(".DS_Store"))
                        continue;

                    FileInputStream fis = new FileInputStream(file);

                    Rank rank = new Rank();
                    rank.setTitle(file.getName());
                    rank.setLevel(1);
                    rank.setBadge(fis);
                    rank.setTasksRequired(1);

                    ranks.add(rank);
                    ranksRepository.insert(rank);

                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }*/




            ObservableList<Rank> ranks = ranksRepository.getAll();
            ranksRepository.loadUsers(ranks);
            ranksTable.setItems(ranks);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Rank, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rank, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Rank, String> tableColumnLevel = new TableColumn<>("Level");
        tableColumnLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<Rank, ImageView> tableColumnImage = new TableColumn<>("Badge");
        tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("badgeView"));

        TableColumn<Rank, ImageView> tableColumnTasksRequired = new TableColumn<>("Tasks Required");
        tableColumnTasksRequired.setCellValueFactory(new PropertyValueFactory<>("tasksRequired"));

        TableColumn<Rank, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Rank, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Rank, Long> tableColumnCreatedUserId = new TableColumn<>("Created User");
        tableColumnCreatedUserId.setCellValueFactory(new PropertyValueFactory<>("createdUser"));

        TableColumn<Rank, Long> tableColumnUpdatedUserId = new TableColumn<>("Updated User");
        tableColumnUpdatedUserId.setCellValueFactory(new PropertyValueFactory<>("updatedUser"));

        ranksTable.getColumns().addAll(tableColumnId, tableColumnTitle,
                tableColumnLevel, tableColumnImage, tableColumnTasksRequired,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUserId, tableColumnUpdatedUserId);
    }

}
