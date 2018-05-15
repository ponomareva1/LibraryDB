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

    public static void insertClient(String first_name, String last_name, String passport_seria, String passport_num) throws SQLException {
        String insertStmt =
                "INSERT INTO CLIENTS " +
                        "(FIRST_NAME, LAST_NAME, PASSPORT_SERIA, PASSPORT_NUM) " +
                        "VALUES" +
                        "('"+first_name+"','"+last_name+"','"+passport_seria+"','"+passport_num+"')";

        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }

    public static void updateClient(String empId, String empEmail) throws SQLException {
        //Declare a UPDATE statement
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE employees\n" +
                        "      SET EMAIL = '" + empEmail + "'\n" +
                        "    WHERE EMPLOYEE_ID = " + empId + ";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void deleteClient(String empId) throws SQLException {
        //Declare a DELETE statement
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM employees\n" +
                        "         WHERE employee_id ="+ empId +";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
