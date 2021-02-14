package com.controllers;

import com.database.RanksRepository;
import com.models.ImageTableCell;
import com.models.Rank;
import javafx.beans.property.SimpleObjectProperty;
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

        TableColumn<Rank, InputStream> tableColumnBadge = new TableColumn<>("Badge");
        tableColumnBadge.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBadge()));
        tableColumnBadge.setCellFactory(param -> new ImageTableCell());

        TableColumn<Rank, ImageView> tableColumnTasksRequired = new TableColumn<>("Tasks Required");
        tableColumnTasksRequired.setCellValueFactory(new PropertyValueFactory<>("tasksRequired"));

        TableColumn<Rank, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Rank, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Rank, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<Rank, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        ranksTable.getColumns().addAll(tableColumnId, tableColumnTitle,
                tableColumnLevel, tableColumnBadge, tableColumnTasksRequired,
                tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
