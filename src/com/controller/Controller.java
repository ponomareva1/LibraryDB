package com.controller;

import com.model.Client;
import com.utils.ControlledScreen;
import com.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class Controller implements ControlledScreen {

    ScreensController myController;

    private ObservableList<Client> clientsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, String> firstNameColumn;

    @FXML
    private TableColumn<Client, String> lastNameColumn;

    @FXML
    private void initialize() {
        initData();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableClients.setItems(clientsData);
    }


    private void initData() {
        DBConnection.dbConnect();

        Connection con = DBConnection.getConnection();
        try {
            Statement st = con.createStatement();
            String query = "select FIRST_NAME, LAST_NAME from CLIENTS";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                clientsData.add(new Client(firstName, lastName));
            }
            st.close();
            DBConnection.dbDisconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
