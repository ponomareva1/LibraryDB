package com.model;

import com.utils.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class JournalDAO {
    public static ObservableList<Journal> searchJournal () {
        String selectStmt = "SELECT * FROM JOURNAL";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getJournalList(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<Journal> getJournalList(ResultSet rs) throws SQLException {
        ObservableList<Journal> journalList = FXCollections.observableArrayList();

        while (rs.next()) {
            Journal journal = new Journal();
            journal.setId(rs.getInt("ID"));
            journal.setBookId(rs.getInt("BOOK_ID"));
            journal.setClientId(rs.getInt("CLIENT_ID"));
            journal.setDateBeg(convertTimestampToString(rs.getTimestamp("DATE_BEG")));
            journal.setDateEnd(convertTimestampToString(rs.getTimestamp("DATE_END")));
            journal.setDateRet(convertTimestampToString(rs.getTimestamp("DATE_RET")));

            journalList.add(journal);
        }
        return journalList;
    }

    private static String convertTimestampToString(Timestamp timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public static void insertBookType(String name, String fine, String dayCount) throws SQLException {
        String insertStmt =
                "INSERT INTO BOOK_TYPES " +
                        "(NAME, FINE, DAY_COUNT) " +
                        "VALUES" +
                        "('"+name+"','"+fine+"','"+dayCount+"')";

        System.out.println("BookType inserted");

        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }

    public static void updateBookType(String id, String name, String fine, String dayCount) throws SQLException {
        String updateStmt = "UPDATE BOOK_TYPES " +
                "SET NAME = '"+name+"', "+
                "FINE = '"+fine+"', "+
                "DAY_COUNT = '"+dayCount+"' "+
                "WHERE ID = "+id+"";

        System.out.println("BookType updated");

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void deleteBookType(String id) throws SQLException {
        String deleteStmt = "DELETE FROM BOOK_TYPES " +
                "WHERE ID = "+ id;

        System.out.println("BookType deleted");

        try {
            DBUtil.dbExecuteUpdate(deleteStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
