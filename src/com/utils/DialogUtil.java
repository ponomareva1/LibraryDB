package com.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

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
        alert.setTitle("Warning");
        showDefault(alert, message);
    }

    public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        showDefault(alert, message);
    }

    public static void showInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        showDefault(alert, message);
    }

    private static void showDefault(Alert alert, String message){
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
