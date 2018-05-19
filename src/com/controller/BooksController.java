package com.controller;

import com.model.Book;
import com.model.BookDAO;
import com.utils.ControlledScreen;
import com.utils.DialogUtil;
import com.utils.ScreensController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;


public class BooksController implements ControlledScreen {

    private ScreensController myController;

    public Button backToMenuButton;
    public Button updateSelectedButton;
    public Button deleteSelectedButton;

    public TextField titleField;
    public TextField authorField;
    public TextField countField;
    public ComboBox typeBox;

    public TableView<Book> tableBooks;
    public TableColumn<Book, Integer> idColumn;
    public TableColumn<Book, String>  titleColumn;
    public TableColumn<Book, String>  authorColumn;
    public TableColumn<Book, Integer> countColumn;
    public TableColumn<Book, Integer> typeColumn;

    ObservableList<String> bookTypes;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty().asObject());

        update();
    }

    private void update() {
        tableBooks.getItems().clear();
        ObservableList<Book> clientsData = BookDAO.searchBooks();
        tableBooks.setItems(clientsData);

        bookTypes = BookDAO.searchBookTypes();
        typeBox.setItems(bookTypes);
    }

    public void userClickedOnTable() {
        if (updateSelectedButton.isDisabled()) this.updateSelectedButton.setDisable(false);
        if (deleteSelectedButton.isDisabled()) this.deleteSelectedButton.setDisable(false);

        setSelectedBook();
    }

    private void setSelectedBook(){
        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        titleField.setText(selectedBook.getTitle());
        authorField.setText(selectedBook.getAuthor());
        countField.setText(selectedBook.getCount().toString());

        typeBox.getSelectionModel().select(selectedBook.getType()-1);
    }

    public void addBook(ActionEvent actionEvent) {
        if (!checkFields()) return;

        if (DialogUtil.checkAction("Insert new book?")){
            try {
                Integer type = typeBox.getSelectionModel().getSelectedIndex() + 1;

                BookDAO.insertBook(titleField.getText(), authorField.getText(),
                        countField.getText(), type.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                update();
            }
        }
    }

    public void updateSelected(ActionEvent actionEvent) {
        if (!checkFields()) return;

        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        String selectedId = selectedBook.getId().toString();

        if (DialogUtil.checkAction("Update book with id = " + selectedId + "?")){
            try {
                Integer type = typeBox.getSelectionModel().getSelectedIndex() + 1;

                BookDAO.updateBook(selectedId, titleField.getText(), authorField.getText(),
                        countField.getText(), type.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                update();
            }
        }
    }

    public void deleteSelected(ActionEvent actionEvent) {
        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        String selectedId = selectedBook.getId().toString();

        if (DialogUtil.checkAction("Delete book with id = " + selectedId + "?")){
            try {
                BookDAO.deleteBook(selectedId);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                update();
            }
        }
    }

    private boolean checkFields(){
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
                countField.getText().isEmpty() || typeBox.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("All fields must contain data");
            return false;
        }

        try{
            Integer.parseInt(countField.getText());
        } catch (NumberFormatException e){
            DialogUtil.showWarning("Count field must contain a number");
            return false;
        }

        return true;
    }

    public void clearFields(ActionEvent actionEvent) {
        titleField.clear();
        authorField.clear();
        countField.clear();
        typeBox.getSelectionModel().clearSelection();
    }
}
