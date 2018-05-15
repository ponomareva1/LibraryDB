package com.model;

import com.utils.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
    public static ObservableList<Client> searchClients () {
        String selectStmt = "SELECT * FROM CLIENTS";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getClientsList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<Client> getClientsList(ResultSet rs) throws SQLException {
        ObservableList<Client> clientsList = FXCollections.observableArrayList();

        while (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("ID"));
            client.setFirstName(rs.getString("FIRST_NAME"));
            client.setLastName(rs.getString("LAST_NAME"));
            client.setPassportSeria(rs.getString("PASSPORT_SERIA"));
            client.setPassportNum(rs.getString("PASSPORT_NUM"));

            clientsList.add(client);
        }
        return clientsList;
    }
}
