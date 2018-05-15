package com.controller;

import com.utils.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController implements ControlledScreen {

    public Button clientsButton;
    private ScreensController myController;


    @FXML
    private void initialize() {
        clientsButton.setOnAction(e -> myController.setScreen("clients"));
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
