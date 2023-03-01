package com.zerobase.wifi.dao;

import com.zerobase.wifi.db.SQLiteDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BookmarkDao extends SQLiteDbConnection {
    public boolean insertBookmarkGroup(String bookmarkName, int bookmarkOrder) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isBookmarkGroupInsert = false;

        final String sql = " INSERT INTO bookmark (bookmark_name, bookmark_order, register_datetime) "
                        + " VALUES (?, ?, ?); ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, bookmarkName);
            preparedStatement.setObject(2, bookmarkOrder);
            preparedStatement.setObject(3, LocalDateTime.now());
            preparedStatement.executeUpdate();

            connection.commit();

            isBookmarkGroupInsert = true;
        } catch (SQLException e) {
            e.printStackTrace();

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }

                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isBookmarkGroupInsert;
    }
}
