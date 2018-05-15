package com.controller;

import com.model.Client;
import com.model.ClientDAO;
import com.utils.ControlledScreen;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Optional;

public class ClientController implements ControlledScreen {

    private ScreensController myController;

    @FXML
    private Button updateSelectedButton;
    @FXML
    private Button deleteSelectedButton;
    @FXML
    private Button backToMenuButton;

    @FXML
    private TextField firstNameAddField;
    @FXML
    private TextField lastNameAddField;
    @FXML
    private TextField passportSeriaAddField;
    @FXML
    private TextField passportNumAddField;

    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, Integer> idColumn;
    @FXML
    private TableColumn<Client, String> firstNameColumn;
    @FXML
    private TableColumn<Client, String> lastNameColumn;
    @FXML
    private TableColumn<Client, String> passportSeriaColumn;
    @FXML
    private TableColumn<Client, String> passportNumColumn;

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        passportSeriaColumn.setCellValueFactory(cellData -> cellData.getValue().passportSeriaProperty());
        passportNumColumn.setCellValueFactory(cellData -> cellData.getValue().passportNumProperty());

        initData();
    }

    private void initData() {
        tableClients.getItems().clear();
        ObservableList<Client> clientsData = ClientDAO.searchClients();
        tableClients.setItems(clientsData);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    public void userClickedOnTable() {
        this.updateSelectedButton.setDisable(false);
        this.deleteSelectedButton.setDisable(false);
    }

    public void addClient(ActionEvent actionEvent) {
        if (!checkFields()){
            return;
        }

        if (checkAction("Insert new client?")){
            try {
                ClientDAO.insertClient(firstNameAddField.getText(),lastNameAddField.getText(),
                        passportSeriaAddField.getText(),passportNumAddField.getText());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                initData();
            }
        }
    }

    public void updateSelected(ActionEvent actionEvent) {

    }

    public void deleteSelected(ActionEvent actionEvent) {

    }

    private boolean checkAction(String question){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(question);

        Optional<ButtonType> result = alert.showAndWait();

        return (result.get() == ButtonType.OK);
    }

    private boolean checkFields(){
        if (firstNameAddField.getText().isEmpty() || lastNameAddField.getText().isEmpty() ||
        passportSeriaAddField.getText().isEmpty() || passportNumAddField.getText().isEmpty()){
            showWarning("All fields must contain data");
            return false;
        }

        if (passportSeriaAddField.getText().length() != 4){
            showWarning("Passport Seria must contain 4 digits");
            return false;
        }

        if (passportNumAddField.getText().length() != 6){
            showWarning("Passport Number must contain 6 digits");
            return false;
        }

        return true;
    }

    private void showWarning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
