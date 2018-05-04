package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Client;

import java.sql.*;

public class Controller {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//localhost:1521/mydb.oracle";
    private static final String DB_USER = "helen";
    private static final String DB_PASSWORD = "oracle";

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

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }


    private void initData() {
        Connection con = getDBConnection();
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
