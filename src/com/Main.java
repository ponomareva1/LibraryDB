package com;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.utils.ScreensController;

public class Main extends Application {

    private static Stage primaryStage;

    public static String screen1ID = "menu";
    private static String screen1File = "/com/view/menu.fxml";
    public static String screen2ID = "clients";
    private static String screen2File = "/com/view/clientsScreen.fxml";
    public static String screen3ID = "books";
    private static String screen3File = "/com/view/booksScreen.fxml";


    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);
        mainContainer.loadScreen(screen3ID, screen3File);

        mainContainer.setScreen(screen3ID);

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
