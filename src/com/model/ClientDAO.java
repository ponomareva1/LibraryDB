package com.model;

import com.utils.DBUtil;
import com.utils.DialogUtil;
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
            DialogUtil.showError(e.getMessage());
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

    public static void insertClient(String first_name, String last_name, String passport_seria, String passport_num) throws SQLException {
        String insertStmt =
                "INSERT INTO CLIENTS " +
                        "(FIRST_NAME, LAST_NAME, PASSPORT_SERIA, PASSPORT_NUM) " +
                        "VALUES" +
                        "('"+first_name+"','"+last_name+"','"+passport_seria+"','"+passport_num+"')";


        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void updateClient(String id, String first_name, String last_name,
                                    String passport_seria, String passport_num) throws SQLException {
        String updateStmt = "UPDATE CLIENTS " +
                "SET FIRST_NAME = '"+first_name+"', "+
                "LAST_NAME = '"+last_name+"', "+
                "PASSPORT_SERIA = '"+passport_seria+"', "+
                "PASSPORT_NUM = '"+passport_num+"' "+
                "WHERE ID = "+id+"";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void deleteClient(String id) throws SQLException {
        String deleteStmt = "DELETE FROM CLIENTS " +
                        "WHERE ID = "+ id;

        try {
            DBUtil.dbExecuteUpdate(deleteStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }
}
