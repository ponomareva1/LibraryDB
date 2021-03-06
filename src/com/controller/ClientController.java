package com.controller;

import com.model.Client;
import com.model.ClientDAO;
import com.utils.ControlledScreen;
import com.utils.DialogUtil;
import com.utils.ScreensController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class ClientController implements ControlledScreen {

    private ScreensController myController;

    public TableView<Client> tableClients;
    public TableColumn<Client, Integer> idColumn;
    public TableColumn<Client, String> firstNameColumn;
    public TableColumn<Client, String> lastNameColumn;
    public TableColumn<Client, String> passportSeriaColumn;
    public TableColumn<Client, String> passportNumColumn;

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField passportSeriaField;
    public TextField passportNumField;

    public Button updateSelectedButton;
    public Button deleteSelectedButton;
    public Button backToMenuButton;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void initialize() {
        backToMenuButton.setOnAction(e -> myController.setScreen("menu"));

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        passportSeriaColumn.setCellValueFactory(cellData -> cellData.getValue().passportSeriaProperty());
        passportNumColumn.setCellValueFactory(cellData -> cellData.getValue().passportNumProperty());

        updateTable();
    }

    private void updateTable() {
        tableClients.getItems().clear();
        ObservableList<Client> clientsData = ClientDAO.searchClients();
        tableClients.setItems(clientsData);
    }

    public void userClickedOnTable() {
        if (updateSelectedButton.isDisabled()) updateSelectedButton.setDisable(false);
        if (deleteSelectedButton.isDisabled()) deleteSelectedButton.setDisable(false);

        setSelectedClient();
    }

    private void setSelectedClient(){
        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();
        firstNameField.setText(selectedClient.getFirstName());
        lastNameField.setText(selectedClient.getLastName());
        passportSeriaField.setText(selectedClient.getPassportSeria());
        passportNumField.setText(selectedClient.getPassportNum());
    }

    public void addClient(ActionEvent actionEvent) {
        if (!checkFields()) return;

        if (DialogUtil.checkAction("Insert new client?")){
            try {
                ClientDAO.insertClient(firstNameField.getText(), lastNameField.getText(),
                        passportSeriaField.getText(), passportNumField.getText());
                DialogUtil.showInformation("New Client inserted!");
            } catch (SQLException e) {
            } finally {
                updateTable();
                this.updateSelectedButton.setDisable(true);
                this.deleteSelectedButton.setDisable(true);
            }
        }
    }

    public void updateSelected(ActionEvent actionEvent) {
        if (!checkFields()) return;

        if (tableClients.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select Row");
            return;
        }
        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();
        String selectedId = selectedClient.getId().toString();

        if (DialogUtil.checkAction("Update client with id = " + selectedId + "?")){
            try {
                ClientDAO.updateClient(selectedId, firstNameField.getText(), lastNameField.getText(),
                        passportSeriaField.getText(), passportNumField.getText());
                DialogUtil.showInformation("Selected Client updated!");
            } catch (SQLException e) {
            } finally {
                updateTable();
                this.updateSelectedButton.setDisable(true);
                this.deleteSelectedButton.setDisable(true);
            }
        }
    }

    public void deleteSelected(ActionEvent actionEvent) {
        if (tableClients.getSelectionModel().isEmpty()){
            DialogUtil.showWarning("Select Row");
            return;
        }
        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();
        String selectedId = selectedClient.getId().toString();
        if (DialogUtil.checkAction("Delete client with id = " + selectedId + "?")){
            try {
                ClientDAO.deleteClient(selectedId);
                DialogUtil.showInformation("Selected Client deleted!");
            } catch (SQLException e) {
            } finally {
                updateTable();
                this.updateSelectedButton.setDisable(true);
                this.deleteSelectedButton.setDisable(true);
            }
        }
    }

    private boolean checkFields(){
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
        passportSeriaField.getText().isEmpty() || passportNumField.getText().isEmpty()){
            DialogUtil.showWarning("All fields must contain data");
            return false;
        }

        if (passportSeriaField.getText().length() != 4){
            DialogUtil.showWarning("Passport Seria must contain 4 digits");
            return false;
        }

        if (passportNumField.getText().length() != 6){
            DialogUtil.showWarning("Passport Number must contain 6 digits");
            return false;
        }

        try{
            Integer.parseInt(passportNumField.getText());
            Integer.parseInt(passportSeriaField.getText());
        } catch (NumberFormatException e){
            DialogUtil.showWarning("Passport Fields must contain a number");
            return false;
        }

        return true;
    }

    public void clearFields(ActionEvent actionEvent) {
        firstNameField.clear();
        lastNameField.clear();
        passportSeriaField.clear();
        passportNumField.clear();
        if (!updateSelectedButton.isDisabled()) updateSelectedButton.setDisable(true);
        if (!deleteSelectedButton.isDisabled()) deleteSelectedButton.setDisable(true);
        updateTable();
    }
}
