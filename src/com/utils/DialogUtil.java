package com.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtil {

    public static boolean checkAction(String question){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(question);

        Optional<ButtonType> result = alert.showAndWait();

        return (result.get() == ButtonType.OK);
    }

    public static void showWarning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
