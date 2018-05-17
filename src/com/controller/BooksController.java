package com.controller;

import com.model.Book;
import com.model.BookDAO;
import com.utils.ControlledScreen;
import com.utils.ScreensController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class BooksController implements ControlledScreen {

    public Button backToMenuButton;
    public Button updateSelectedButton;
    public Button deleteSelectedButton;

    public TextField titleField;
    public TextField authorField;
    public TextField countField;
    public ComboBox typeBox;

    private ScreensController myController;

    public TableView<Book> tableBooks;
    public TableColumn<Book, Integer> idColumn;
    public TableColumn<Book, String> titleColumn;
    public TableColumn<Book, String> authorColumn;
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

        //typeBox.setPromptText(typeBox.getConverter().toString(typeBox.getValue()));
        typeBox.setPromptText(bookTypes.get(selectedBook.getType()-1));
    }
}
