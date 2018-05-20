package com.controller;

import com.model.Book;
import com.model.BookType;
import com.model.BookTypeDAO;
import com.utils.ControlledScreen;
import com.utils.DialogUtil;
import com.utils.ScreensController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class BookTypesController implements ControlledScreen {

    private ScreensController myController;

    public TableView<BookType> tableBookTypes;
    public TableColumn<BookType, Integer> idColumn;
    public TableColumn<BookType, String>  nameColumn;
    public TableColumn<BookType, Integer> fineColumn;
    public TableColumn<BookType, Integer> dayCountColumn;

    public Button backToMenuButton;
    public Button updateSelectedButton;
    public Button deleteSelectedButton;

    public TextField nameField;
    public TextField fineField;
    public TextField dayCountField;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        fineColumn.setCellValueFactory(cellData -> cellData.getValue().fineProperty().asObject());
        dayCountColumn.setCellValueFactory(cellData -> cellData.getValue().dayCountProperty().asObject());

        updateTable();
    }

    private void updateTable() {
        tableBookTypes.getItems().clear();
        ObservableList<BookType> clientsData = BookTypeDAO.searchBookTypes();
        tableBookTypes.setItems(clientsData);
    }

    public void userClickedOnTable(MouseEvent mouseEvent) {
        if (updateSelectedButton.isDisabled()) this.updateSelectedButton.setDisable(false);
        if (deleteSelectedButton.isDisabled()) this.deleteSelectedButton.setDisable(false);

        setSelectedBookType();
    }

    private void setSelectedBookType() {
        BookType selectedBookType = tableBookTypes.getSelectionModel().getSelectedItem();
        nameField.setText(selectedBookType.getName());
        fineField.setText(selectedBookType.getFine().toString());
        dayCountField.setText(selectedBookType.getDayCount().toString());
    }

    public void addBookType(ActionEvent actionEvent) {
        if (!checkFields()) return;

        if (DialogUtil.checkAction("Insert new book type?")){
            try {
                BookTypeDAO.insertBookType(nameField.getText(), fineField.getText(),
                        dayCountField.getText());
                DialogUtil.showInformation("New Book Type inserted!");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                updateTable();
                this.updateSelectedButton.setDisable(true);
                this.deleteSelectedButton.setDisable(true);
            }
        }
    }

    public void updateSelected(ActionEvent actionEvent) {
        if (!checkFields()) return;

        BookType selectedBookType = tableBookTypes.getSelectionModel().getSelectedItem();
        String selectedId = selectedBookType.getId().toString();

        if (DialogUtil.checkAction("Update book type with id = " + selectedId + "?")){
            try {
                BookTypeDAO.updateBookType(selectedId, nameField.getText(), fineField.getText(),
                        dayCountField.getText());
                DialogUtil.showInformation("Selected Book Type updated!");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                updateTable();
                this.updateSelectedButton.setDisable(true);
                this.deleteSelectedButton.setDisable(true);
            }
        }
    }

    public void deleteSelected(ActionEvent actionEvent) {
        BookType selectedBookType = tableBookTypes.getSelectionModel().getSelectedItem();
        String selectedId = selectedBookType.getId().toString();

        if (DialogUtil.checkAction("Delete book type with id = " + selectedId + "?")){
            try {
                BookTypeDAO.deleteBookType(selectedId);
                DialogUtil.showInformation("Selected Book Type deleted!");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                updateTable();
                this.updateSelectedButton.setDisable(true);
                this.deleteSelectedButton.setDisable(true);
            }
        }
    }

    private boolean checkFields(){
        if (nameField.getText().isEmpty() || fineField.getText().isEmpty() ||
                dayCountField.getText().isEmpty()){
            DialogUtil.showWarning("All fields must contain data");
            return false;
        }

        try{
            Integer.parseInt(fineField.getText());
            Integer.parseInt(dayCountField.getText());
        } catch (NumberFormatException e){
            DialogUtil.showWarning("Fine and Day Count fields must contain a number");
            return false;
        }

        return true;
    }

    public void clearFields(ActionEvent actionEvent) {
        nameField.clear();
        fineField.clear();
        dayCountField.clear();
    }
}
