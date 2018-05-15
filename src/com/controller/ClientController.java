package com.controller;

import com.model.Client;
import com.model.ClientDAO;
import com.utils.ControlledScreen;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClientController implements ControlledScreen {

    ScreensController myController;

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

        initData();
    }


    private void initData() {
        ObservableList<Client> clientsData = ClientDAO.searchClients();
        tableClients.setItems(clientsData);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
