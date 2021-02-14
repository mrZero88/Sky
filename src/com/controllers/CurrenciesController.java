package com.controllers;

import com.database.BugsRepository;
import com.database.CurrenciesRepository;
import com.models.Bug;
import com.models.Currency;
import com.models.Technology;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class CurrenciesController {

    @FXML
    private TableView currenciesTable;

    public CurrenciesController() {

    }

    @FXML
    public void initialize() {
        try {
            CurrenciesRepository currenciesRepository = new CurrenciesRepository();
            ObservableList<Currency> currencies = currenciesRepository.getAll();
            currenciesRepository.loadUsers(currencies);
            currenciesTable.setItems(currencies);
            addTableColumns();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addTableColumns() {
        TableColumn<Currency, Long> tableColumnId = new TableColumn<>("Id");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Currency, String> tableColumnTitle = new TableColumn<>("Title");
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Currency, String> tableColumnSymbol = new TableColumn<>("Symbol");
        tableColumnSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Currency, String> tableColumnIso = new TableColumn<>("Iso");
        tableColumnIso.setCellValueFactory(new PropertyValueFactory<>("iso"));

        TableColumn<Currency, Timestamp> tableColumnCreatedAt = new TableColumn<>("Created At");
        tableColumnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Currency, Timestamp> tableColumnUpdatedAt = new TableColumn<>("Updated At");
        tableColumnUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        TableColumn<Currency, String> tableColumnCreatedUser = new TableColumn<>("Created User");
        tableColumnCreatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        TableColumn<Currency, String> tableColumnUpdatedUser = new TableColumn<>("Updated User");
        tableColumnUpdatedUser.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreatedUser().getAbbreviation()));

        currenciesTable.getColumns().addAll(tableColumnId, tableColumnTitle,
                tableColumnSymbol, tableColumnIso, tableColumnCreatedAt, tableColumnUpdatedAt,
                tableColumnCreatedUser, tableColumnUpdatedUser);
    }

}
