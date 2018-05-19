package com.controller;

import com.model.Journal;
import com.model.JournalDAO;
import com.utils.ControlledScreen;
import com.utils.ScreensController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class JournalController implements ControlledScreen {

    public TableView<Journal> tableClients;
    public TableColumn<Journal, Integer> idColumn;
    public TableColumn<Journal, Integer> bookIdColumn;
    public TableColumn<Journal, Integer> clientIdColumn;
    public TableColumn<Journal, String> dateBegColumn;
    public TableColumn<Journal, String> dateEndColumn;
    public TableColumn<Journal, String> dateRetColumn;

    public Button backToMenuButton;

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty().asObject());
        clientIdColumn.setCellValueFactory(cellData -> cellData.getValue().clientIdProperty().asObject());
        dateBegColumn.setCellValueFactory(cellData -> cellData.getValue().dateBegProperty());
        dateEndColumn.setCellValueFactory(cellData -> cellData.getValue().dateEndProperty());
        dateRetColumn.setCellValueFactory(cellData -> cellData.getValue().dateRetProperty());

        updateTable();
    }

    private void updateTable() {
        tableClients.getItems().clear();
        ObservableList<Journal> clientsData = JournalDAO.searchJournal();
        tableClients.setItems(clientsData);
    }
}
