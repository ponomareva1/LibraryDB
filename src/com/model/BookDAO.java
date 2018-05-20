package com.model;

import com.utils.DBUtil;
import com.utils.DialogUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO {

    public static ObservableList<String> searchBookTypes () {
        String selectStmt = "SELECT NAME FROM BOOK_TYPES";
        ObservableList<String> bookTypes = FXCollections.observableArrayList();

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            while (rs.next()) {
                bookTypes.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return bookTypes;
    }

    public static ObservableList<Book> searchBooks () {
        String selectStmt = "SELECT * FROM BOOKS";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getBooksList(rs);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return null;
    }

    private static ObservableList<Book> getBooksList(ResultSet rs) throws SQLException {
        ObservableList<Book> booksList = FXCollections.observableArrayList();

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("ID"));
            book.setTitle(rs.getString("NAME"));
            book.setAuthor(rs.getString("AUTHOR"));
            book.setCount(rs.getInt("CNT"));
            book.setType(rs.getInt("TYPE_ID"));

            booksList.add(book);
        }
        return booksList;
    }

    public static void insertBook(String name, String author, String count, String type) throws SQLException {
        String insertStmt =
                "INSERT INTO BOOKS " +
                        "(NAME, AUTHOR, CNT, TYPE_ID) " +
                        "VALUES" +
                        "('"+name+"','"+author+"','"+count+"','"+type+"')";

        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void updateBook(String id, String name, String author, String count, String type) throws SQLException {
        String updateStmt = "UPDATE BOOKS " +
                "SET NAME = '"+name+"', "+
                "AUTHOR = '"+author+"', "+
                "CNT = '"+count+"', "+
                "TYPE_ID = '"+type+"' "+
                "WHERE ID = "+id+"";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void deleteBook(String id) throws SQLException {
        String deleteStmt = "DELETE FROM BOOKS " +
                "WHERE ID = "+ id;

        try {
            DBUtil.dbExecuteUpdate(deleteStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }
}
