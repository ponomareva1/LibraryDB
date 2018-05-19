package com.utils;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//localhost:1521/mydb.oracle";
    private static final String DB_USER = "helen";
    private static final String DB_PASSWORD = "oracle";

    private static Connection connection;

    private static void dbConnect() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection closing failed.");
            e.printStackTrace();
        }
    }

    // for Select
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs;
        try {
            dbConnect();

            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }

    // for Update/Insert/Delete
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

    // function with 1 integer parameter
    public static Integer dbExecuteFUN1(String sqlStmt, Integer param) throws SQLException {
        CallableStatement cs = null;
        try {
            dbConnect();
            cs = connection.prepareCall(sqlStmt);
            cs.registerOutParameter(1,Types.NUMERIC);
            cs.setInt(2, param);
            cs.executeUpdate();
            return cs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Problem occurred at dbExecuteFUN1 operation : " + e);
            throw e;
        } finally {
            if (cs != null) {
                cs.close();
            }
            dbDisconnect();
        }
    }
}
