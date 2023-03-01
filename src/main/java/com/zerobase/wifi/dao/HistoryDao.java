package com.zerobase.wifi.dao;

import com.zerobase.wifi.db.SQLiteDbConnection;
import com.zerobase.wifi.dto.PublicWifiDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class HistoryDao extends SQLiteDbConnection {
    public void insertHistory(double latitude, double longitude) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String sql = " INSERT INTO history (longitude, latitude, check_datetime) "
                        + " VALUES (?, ?, ?); " ;
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, latitude);
            preparedStatement.setObject(2, longitude);
            preparedStatement.setObject(3, LocalDateTime.now());

            preparedStatement.executeUpdate();
            connection.commit();
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
    }
}
