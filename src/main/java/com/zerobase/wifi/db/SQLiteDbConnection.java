package com.zerobase.wifi.db;

import java.sql.*;

public class SQLiteDbConnection {
    private static Connection conn = null;

    public static Connection getDbConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:/Users/joy/jin-zerobase/과제/zerobase-mission-1/public_wifi.db"); // 절대경로 설정
                conn.setAutoCommit(false);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeDbConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }
}
