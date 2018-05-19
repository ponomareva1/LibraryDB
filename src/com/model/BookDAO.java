package com.model;

import com.utils.DBUtil;
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
            System.out.println("SQL select operation has been failed: " + e);
        }

        return bookTypes;
    }

    public static ObservableList<Book> searchBooks () {
        String selectStmt = "SELECT * FROM BOOKS";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getBooksList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
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

        System.out.println("Book inserted");

        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
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

        System.out.println("Book updated");

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void deleteBook(String id) throws SQLException {
        String deleteStmt = "DELETE FROM BOOKS " +
                "WHERE ID = "+ id;

        System.out.println("Book deleted");

        try {
            DBUtil.dbExecuteUpdate(deleteStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
