package com.controller;

import com.model.Journal;
import com.model.JournalDAO;
import com.utils.ControlledScreen;
import com.utils.DialogUtil;
import com.utils.ScreensController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class JournalController implements ControlledScreen {

    public TableView<Journal> tableClients;
    public TableColumn<Journal, Integer> idColumn;
    public TableColumn<Journal, Integer> bookIdColumn;
    public TableColumn<Journal, Integer> clientIdColumn;
    public TableColumn<Journal, String> dateBegColumn;
    public TableColumn<Journal, String> dateEndColumn;
    public TableColumn<Journal, String> dateRetColumn;

    public Button backToMenuButton;
    public Button resetButton;

    public ComboBox clientIdBox;
    public ComboBox bookIdBox;

    public TextField numOfBooksField;
    public TextField currentFineField;

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));
        resetButton.setOnAction(e -> updateTable());

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
        ObservableList<Journal> data = JournalDAO.searchJournal();
        tableClients.setItems(data);
        tableClients.getSortOrder().add(idColumn);

        clientIdBox.setItems(JournalDAO.searchClientIDs());
        clientIdBox.getSelectionModel().clearSelection();
        bookIdBox.setItems(JournalDAO.searchBookIDs());
        bookIdBox.getSelectionModel().clearSelection();

        numOfBooksField.clear();
        currentFineField.clear();
    }

    public void searchClient(ActionEvent actionEvent) {
        if (clientIdBox.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select client ID");
            return;
        }
        Integer clientID = Integer.parseInt(clientIdBox.getSelectionModel().getSelectedItem().toString());
        tableClients.getItems().clear();
        ObservableList<Journal> data = JournalDAO.searchJournalForClient(clientID);
        tableClients.setItems(data);
        tableClients.getSortOrder().add(idColumn);

        numOfBooksField.setText(JournalDAO.getNumOfBooks(clientID));
        currentFineField.setText(JournalDAO.getClientFine(clientID));
    }

    public void addNewRecord(ActionEvent event) {
        if (clientIdBox.getSelectionModel().isEmpty() || bookIdBox.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select client ID and book ID");
            return;
        }

    }

    public void returnBook(ActionEvent event) {

    }
}
