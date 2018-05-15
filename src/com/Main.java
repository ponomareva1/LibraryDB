package com;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.controller.ScreensController;

public class Main extends Application {

    private static Stage primaryStage;

    public static String screen1ID = "menu";
    private static String screen1File = "/com/view/menu.fxml";
    public static String screen2ID = "clients";
    private static String screen2File = "/com/view/clientsScreen.fxml";


    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);

        mainContainer.setScreen(screen2ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void resizeScreen() {
        primaryStage.sizeToScene(); primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
