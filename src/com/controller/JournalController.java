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

import java.sql.SQLException;

public class JournalController implements ControlledScreen {

    private ScreensController myController;

    public TableView<Journal> tableJournal;
    public TableColumn<Journal, Integer> idColumn;
    public TableColumn<Journal, String> bookIdColumn;
    public TableColumn<Journal, String > clientIdColumn;
    public TableColumn<Journal, String> dateBegColumn;
    public TableColumn<Journal, String> dateEndColumn;
    public TableColumn<Journal, String> dateRetColumn;

    public TextField numOfBooksField;
    public TextField currentFineField;

    public Button backToMenuButton;
    public Button resetButton;

    public ComboBox clientIdBox;
    public ComboBox bookIdBox;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));
        resetButton.setOnAction(e -> updateTable());

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        clientIdColumn.setCellValueFactory(cellData -> cellData.getValue().clientIdProperty());
        dateBegColumn.setCellValueFactory(cellData -> cellData.getValue().dateBegProperty());
        dateEndColumn.setCellValueFactory(cellData -> cellData.getValue().dateEndProperty());
        dateRetColumn.setCellValueFactory(cellData -> cellData.getValue().dateRetProperty());

        updateTable();
    }

    private void updateTable() {
        tableJournal.getItems().clear();
        ObservableList<Journal> data = JournalDAO.searchJournal();
        tableJournal.setItems(data);
        tableJournal.getSortOrder().add(idColumn);

        clientIdBox.setItems(JournalDAO.searchClients());
        clientIdBox.getSelectionModel().clearSelection();
        bookIdBox.setItems(JournalDAO.searchBooks());
        bookIdBox.getSelectionModel().clearSelection();

        numOfBooksField.clear();
        currentFineField.clear();
    }

    public void searchClient(ActionEvent actionEvent) {
        if (clientIdBox.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select client ID");
            return;
        }
        Integer clientID = JournalDAO.getClientIDByName(clientIdBox.getSelectionModel().getSelectedItem().toString());
        tableJournal.getItems().clear();
        ObservableList<Journal> data = JournalDAO.searchJournalForClient(clientID);
        tableJournal.setItems(data);
        tableJournal.getSortOrder().add(idColumn);

        numOfBooksField.setText(JournalDAO.getNumOfBooks(clientID));
        currentFineField.setText(JournalDAO.getClientFine(clientID));
    }

    public void addNewRecord(ActionEvent event) {
        if (clientIdBox.getSelectionModel().isEmpty() || bookIdBox.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select client ID and book ID");
            return;
        }
        Integer clientID = JournalDAO.getClientIDByName(clientIdBox.getSelectionModel().getSelectedItem().toString());
        Integer bookId = JournalDAO.getBookIDByName(bookIdBox.getSelectionModel().getSelectedItem().toString());

        if (DialogUtil.checkAction("Issue book with ID = " + bookId.toString() +
                " to client with ID = " + clientID.toString() + "?")){
            try {
                JournalDAO.callAddNewRecord(clientID, bookId);
                DialogUtil.showInformation("The book is issued to the client!");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                updateTable();
            }
        }
    }

    public void returnBook(ActionEvent event) {
        if (tableJournal.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select Journal Row");
            return;
        }
        Journal selectedRow = tableJournal.getSelectionModel().getSelectedItem();
        Integer clientID = JournalDAO.getClientIDByName(selectedRow.getClientId());
        Integer bookId = JournalDAO.getBookIDByName(selectedRow.getBookId());

        if (selectedRow.getDateRet() != "NULL"){
            DialogUtil.showWarning("Date of Return must be NULL");
            return;
        }

        if (DialogUtil.checkAction("Return book with ID = " + bookId.toString() +
                " from client with ID = " + clientID.toString() + "?")){
            try {
                JournalDAO.callReturnBook(clientID, bookId);
                DialogUtil.showInformation("Book returned!");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                updateTable();
            }
        }
    }

    public void showMaxFine(ActionEvent actionEvent) {
        DialogUtil.showInformation(JournalDAO.getMaxFine());
    }

    public void showPopularBooks(ActionEvent actionEvent) {
        DialogUtil.showInformation(JournalDAO.get3PopularBooks());
    }
}
