package com.model;

import com.utils.DBUtil;
import com.utils.DialogUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class JournalDAO {

    public static ObservableList<String> searchClients () {
        String selectStmt = "SELECT FIRST_NAME, LAST_NAME FROM CLIENTS ORDER BY FIRST_NAME";
        ObservableList<String> clients = FXCollections.observableArrayList();

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            while (rs.next()) {
                String name = rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME");
                clients.add(name);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return clients;
    }

    public static ObservableList<String> searchBooks () {
        String selectStmt = "SELECT NAME FROM BOOKS WHERE CNT != 0 ORDER BY NAME";
        ObservableList<String> books = FXCollections.observableArrayList();

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            while (rs.next()) {
                books.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return books;
    }

    public static String getNumOfBooks(Integer clientID){
        String callStmt = "{? = call NUM_BOOKS_ON_HANDS(?)}";
        Integer numOfBooks = 0;

        try {
            numOfBooks = DBUtil.dbExecuteFUN1(callStmt, clientID);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return numOfBooks.toString();
    }

    public static String getClientFine(Integer clientID){
        String callStmt = "{? = call CLIENT_FINE_AMOUNT(?)}";
        Integer fineAmount = 0;

        try {
            fineAmount = DBUtil.dbExecuteFUN1(callStmt, clientID);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return fineAmount.toString();
    }

    public static String getMaxFine(){
        String callStmt = "{? = call MAX_FINE()}";
        String fineAmount = "";

        try {
            fineAmount = DBUtil.dbExecuteFUN0(callStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return "Current max fine: " + fineAmount;
    }

    public static String get3PopularBooks(){
        String callStmt = "{call POPULAR_BOOKS_3(?,?,?)}";
        ArrayList<String> books = new ArrayList<>();
        try {
            books = DBUtil.dbExecutePROC3OUT(callStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        String msg = "3 most popular books:\n\n";
        msg += books.get(0) + '\n';
        msg += books.get(1) + '\n';
        msg += books.get(2);

        return msg;
    }

    public static Integer getClientIDByName(String clientName) {
        String selectStmt = "SELECT ID FROM CLIENTS WHERE FIRST_NAME || ' ' || LAST_NAME = '" +
                clientName + "'";
        Integer clientID = 0;
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            rs.next();
            clientID = rs.getInt("ID");
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return clientID;
    }

    public static Integer getBookIDByName(String bookName) {
        String selectStmt = "SELECT ID FROM BOOKS WHERE NAME = '" +
                bookName + "'";
        Integer bookID = 0;
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            rs.next();
            bookID = rs.getInt("ID");
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return bookID;
    }

    public static ObservableList<Journal> searchJournal () {
        String selectStmt = "SELECT J.ID, B.NAME AS BOOK_ID, C.FIRST_NAME || ' ' || C.LAST_NAME AS CLIENT_ID, \n" +
                "J.DATE_BEG, J.DATE_END, J.DATE_RET\n" +
                "FROM JOURNAL J JOIN BOOKS B ON J.BOOK_ID = B.ID JOIN CLIENTS C ON J.CLIENT_ID = C.ID\n" +
                "ORDER BY J.ID";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getJournalList(rs);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return null;
    }

    public static ObservableList<Journal> searchJournalForClient(Integer clientID) {
        String selectStmt = "SELECT J.ID, B.NAME AS BOOK_ID, C.FIRST_NAME || ' ' || C.LAST_NAME AS CLIENT_ID, \n" +
                "J.DATE_BEG, J.DATE_END, J.DATE_RET\n" +
                "FROM JOURNAL J JOIN BOOKS B ON J.BOOK_ID = B.ID JOIN CLIENTS C ON J.CLIENT_ID = C.ID\n" +
                "WHERE CLIENT_ID = " + clientID.toString();

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getJournalList(rs);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return null;
    }

    private static ObservableList<Journal> getJournalList(ResultSet rs) throws SQLException {
        ObservableList<Journal> journalList = FXCollections.observableArrayList();

        while (rs.next()) {
            Journal journal = new Journal();
            journal.setId(rs.getInt("ID"));
            journal.setBookId(rs.getString("BOOK_ID"));
            journal.setClientId(rs.getString("CLIENT_ID"));
            journal.setDateBeg(convertTimestampToString(rs.getTimestamp("DATE_BEG")));
            journal.setDateEnd(convertTimestampToString(rs.getTimestamp("DATE_END")));

            try {
                journal.setDateRet(convertTimestampToString(rs.getTimestamp("DATE_RET")));
            } catch (NullPointerException e){
                journal.setDateRet("NULL");
            }


            journalList.add(journal);
        }
        return journalList;
    }

    private static String convertTimestampToString(Timestamp timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(timestamp);
    }

    public static void callReturnBook(Integer clientID, Integer bookID) throws SQLException {
        String procStmt = "{call RETURN_BOOK(?,?)}";

        try {
            DBUtil.dbExecutePROC2IN(procStmt, clientID, bookID);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }

    public static void callAddNewRecord(Integer clientID, Integer bookID) throws SQLException {
        String procStmt = "{call ADD_NEW_JOURNAL_RECORD(?,?)}";

        try {
            DBUtil.dbExecutePROC2IN(procStmt, clientID, bookID);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
            throw e;
        }
    }
}
