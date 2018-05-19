package com.controller;

import com.utils.ControlledScreen;
import com.utils.ScreensController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController implements ControlledScreen {

    public Button clientsButton;
    public Button booksButton;
    public Button bookTypesButton;
    public Button journalButton;

    private ScreensController myController;

    @FXML
    private void initialize() {
        journalButton.setOnAction(e -> myController.setScreen("journal"));
        clientsButton.setOnAction(e -> myController.setScreen("clients"));
        booksButton.setOnAction(e -> myController.setScreen("books"));
        bookTypesButton.setOnAction(e -> myController.setScreen("bookTypes"));
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
