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

    public static ObservableList<Integer> searchClientIDs () {
        String selectStmt = "SELECT DISTINCT CLIENT_ID FROM JOURNAL ORDER BY CLIENT_ID";
        ObservableList<Integer> clientIDs = FXCollections.observableArrayList();

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            while (rs.next()) {
                clientIDs.add(rs.getInt("CLIENT_ID"));
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return clientIDs;
    }

    public static ObservableList<Integer> searchBookIDs () {
        String selectStmt = "SELECT DISTINCT ID FROM BOOKS WHERE CNT != 0 ORDER BY ID";
        ObservableList<Integer> bookIDs = FXCollections.observableArrayList();

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            while (rs.next()) {
                bookIDs.add(rs.getInt("ID"));
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }

        return bookIDs;
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
        Integer fineAmount = 0;

        try {
            fineAmount = DBUtil.dbExecuteFUN0(callStmt);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return "Current max fine: " + fineAmount.toString();
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

    public static ObservableList<Journal> searchJournal () {
        String selectStmt = "SELECT * FROM JOURNAL";

        try {
            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

            return getJournalList(rs);
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return null;
    }

    public static ObservableList<Journal> searchJournalForClient(Integer clientID) {
        String selectStmt = "SELECT * FROM JOURNAL WHERE CLIENT_ID = " + clientID.toString();

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
            journal.setBookId(rs.getInt("BOOK_ID"));
            journal.setClientId(rs.getInt("CLIENT_ID"));
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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
