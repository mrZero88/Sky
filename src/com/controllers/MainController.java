package com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;

public class MainController {

    private final double VIEW_MARGIN = 10.0;

    @FXML
    private MenuBar menuBar;

    @FXML
    private AnchorPane rootAnchor;

    public MainController() {
    }

    @FXML
    public void initialize() {
        //menuBar.setUseSystemMenuBar(true);
    }

    @FXML
    public void onProjectsClicked() throws Exception {
        Parent projectsView = FXMLLoader.load(getClass().getResource("../pages/ProjectsView.fxml"));
        AnchorPane.setLeftAnchor(projectsView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(projectsView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(projectsView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(projectsView, VIEW_MARGIN);
        rootAnchor.getChildren().add(projectsView);
    }

    @FXML
    public void onNewProjectClicked() throws Exception {
        System.out.println("Hello New Project");
    }

    @FXML
    public void onFeaturesClicked() throws Exception {
        Parent featuresView = FXMLLoader.load(getClass().getResource("../pages/FeaturesView.fxml"));
        AnchorPane.setLeftAnchor(featuresView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(featuresView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(featuresView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(featuresView, VIEW_MARGIN);
        rootAnchor.getChildren().add(featuresView);
    }

    @FXML
    public void onNewFeatureClicked() throws Exception {

    }

    @FXML
    public void onBugsClicked() throws Exception {
        Parent bugsView = FXMLLoader.load(getClass().getResource("../pages/BugsView.fxml"));
        AnchorPane.setLeftAnchor(bugsView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(bugsView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(bugsView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(bugsView, VIEW_MARGIN);
        rootAnchor.getChildren().add(bugsView);
    }

    @FXML
    public void onNewBugClicked() {
        System.out.println("Hello New Bug");

    }

    @FXML
    public void onCustomersClicked() throws Exception {
        Parent customersView = FXMLLoader.load(getClass().getResource("../pages/CustomersView.fxml"));
        AnchorPane.setLeftAnchor(customersView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(customersView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(customersView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(customersView, VIEW_MARGIN);
        rootAnchor.getChildren().add(customersView);
    }

    @FXML
    public void onNewCustomerClicked() {
        System.out.println("Hello New Customer");

    }

    @FXML
    public void onUsersClicked() throws Exception {
        Parent usersView = FXMLLoader.load(getClass().getResource("../pages/UsersView.fxml"));
        AnchorPane.setLeftAnchor(usersView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(usersView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(usersView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(usersView, VIEW_MARGIN);
        rootAnchor.getChildren().add(usersView);
    }

    @FXML
    public void onNewUserClicked() {
        System.out.println("Hello New User");

    }

    @FXML
    public void onCountriesClicked() throws Exception {
        Parent countriesView = FXMLLoader.load(getClass().getResource("../pages/CountriesView.fxml"));
        AnchorPane.setLeftAnchor(countriesView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(countriesView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(countriesView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(countriesView, VIEW_MARGIN);
        rootAnchor.getChildren().add(countriesView);
    }

    @FXML
    public void onNewCountryClicked() {
        System.out.println("Hello New User");

    }

    @FXML
    public void onCurrenciesClicked() throws Exception {
        Parent currenciesView = FXMLLoader.load(getClass().getResource("../pages/CurrenciesView.fxml"));
        AnchorPane.setLeftAnchor(currenciesView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(currenciesView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(currenciesView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(currenciesView, VIEW_MARGIN);
        rootAnchor.getChildren().add(currenciesView);
    }

    @FXML
    public void onNewCurrencyClicked() {
        System.out.println("Hello New User");

    }

    @FXML
    public void onTechnologiesClicked() throws Exception {
        Parent technologiesView = FXMLLoader.load(getClass().getResource("../pages/TechnologiesView.fxml"));
        AnchorPane.setLeftAnchor(technologiesView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(technologiesView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(technologiesView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(technologiesView, VIEW_MARGIN);
        rootAnchor.getChildren().add(technologiesView);
    }

    @FXML
    public void onNewTechnologyClicked() {
        System.out.println("Hello New User");

    }

    @FXML
    public void onProjectStatesClicked() throws Exception {
        Parent projectStatesView = FXMLLoader.load(getClass().getResource("../pages/ProjectStatesView.fxml"));
        AnchorPane.setLeftAnchor(projectStatesView, VIEW_MARGIN);
        AnchorPane.setTopAnchor(projectStatesView, VIEW_MARGIN);
        AnchorPane.setRightAnchor(projectStatesView, VIEW_MARGIN);
        AnchorPane.setBottomAnchor(projectStatesView, VIEW_MARGIN);
        rootAnchor.getChildren().add(projectStatesView);
    }

    @FXML
    public void onNewProjectStateClicked() {
        System.out.println("Hello New User");

    }

}
