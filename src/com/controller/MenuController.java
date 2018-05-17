package com.controller;

import com.utils.ControlledScreen;
import com.utils.ScreensController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController implements ControlledScreen {

    public Button clientsButton;
    public Button booksButton;
    private ScreensController myController;


    @FXML
    private void initialize() {
        clientsButton.setOnAction(e -> myController.setScreen("clients"));
        booksButton.setOnAction(e -> myController.setScreen("books"));
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
