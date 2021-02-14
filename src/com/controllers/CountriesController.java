package com.controllers;

import com.database.BugsRepository;
import com.database.CountriesRepository;
import com.models.Bug;
import com.models.Country;
import com.models.ImageTableCell;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.Arrays;

public class CountriesController {

    @FXML
    private TableView countriesTable;

    public CountriesController() {

    }

    @FXML
    public void initialize() {
        try {
            CountriesRepository countriesRepository = new CountriesRepository();
            ObservableList<Country> countries = countriesRepository.getAll();
            countriesRepository.loadUsers(countries);
            countriesTable.setItems(countries);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Country, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Country, String> tableColumnName = new TableColumn<>("Name");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Country, String> tableColumnShortcut = new TableColumn<>("Shortcut");
        tableColumnShortcut.setCellValueFactory(new PropertyValueFactory<>("shortcut"));

        TableColumn<Country, InputStream> tableColumnFlag = new TableColumn<>("Flag");
        tableColumnFlag.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFlag()));
        tableColumnFlag.setCellFactory(param -> new ImageTableCell());

        TableColumn<Country, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Country, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Country, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<Country, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        countriesTable.getColumns().addAll(tableColumnId, tableColumnName,
                tableColumnShortcut, tableColumnFlag, tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
