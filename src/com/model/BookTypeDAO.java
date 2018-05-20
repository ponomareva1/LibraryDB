package com.model;

import com.utils.DBUtil;
import com.utils.DialogUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTypeDAO {
    public static ObservableList<BookType> searchBookTypes () {
        String selectStmt = "SELECT * FROM BOOK_TYPES";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getBookTypesList(rs);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return null;
    }

    private static ObservableList<BookType> getBookTypesList(ResultSet rs) throws SQLException {
        ObservableList<BookType> booksList = FXCollections.observableArrayList();

        while (rs.next()) {
            BookType bookType = new BookType();
            bookType.setId(rs.getInt("ID"));
            bookType.setName(rs.getString("NAME"));
            bookType.setFine(rs.getInt("FINE"));
            bookType.setDayCount(rs.getInt("DAY_COUNT"));

            booksList.add(bookType);
        }
        return booksList;
    }

    public static void insertBookType(String name, String fine, String dayCount) throws SQLException {
        String insertStmt =
                "INSERT INTO BOOK_TYPES " +
                        "(NAME, FINE, DAY_COUNT) " +
                        "VALUES" +
                        "('"+name+"','"+fine+"','"+dayCount+"')";

        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void updateBookType(String id, String name, String fine, String dayCount) throws SQLException {
        String updateStmt = "UPDATE BOOK_TYPES " +
                "SET NAME = '"+name+"', "+
                "FINE = '"+fine+"', "+
                "DAY_COUNT = '"+dayCount+"' "+
                "WHERE ID = "+id+"";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void deleteBookType(String id) throws SQLException {
        String deleteStmt = "DELETE FROM BOOK_TYPES " +
                "WHERE ID = "+ id;

        try {
            DBUtil.dbExecuteUpdate(deleteStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }
}
