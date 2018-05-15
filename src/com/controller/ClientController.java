package com.controller;

import com.model.Client;
import com.model.ClientDAO;
import com.utils.ControlledScreen;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClientController implements ControlledScreen {

    private ScreensController myController;

    @FXML
    private Button updateSelectedButton;
    @FXML
    private Button deleteSelectedButton;

    @FXML
    private TextField passportNumAddField;
    @FXML
    private TextField passportSeriaAddField;
    @FXML
    private TextField lastNameAddField;
    @FXML
    private TextField firstNameAddField;

    @FXML
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, Integer> idColumn;

    @FXML
    private TableColumn<Client, String> firstNameColumn;

    @FXML
    private TableColumn<Client, String> lastNameColumn;

    @FXML
    private TableColumn<Client, String> passportSeriaColumn;

    @FXML
    private TableColumn<Client, String> passportNumColumn;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        passportSeriaColumn.setCellValueFactory(cellData -> cellData.getValue().passportSeriaProperty());
        passportNumColumn.setCellValueFactory(cellData -> cellData.getValue().passportNumProperty());

        // init data
        ObservableList<Client> clientsData = ClientDAO.searchClients();
        tableClients.setItems(clientsData);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    public void userClickedOnTable() {
        this.updateSelectedButton.setDisable(false);
        this.deleteSelectedButton.setDisable(false);
    }

    public void addClient(ActionEvent actionEvent) {

    }

    public void updateSelected(ActionEvent actionEvent) {

    }

    public void deleteSelected(ActionEvent actionEvent) {

    }

    public void goBackToMenu(ActionEvent actionEvent) {

    }
}
