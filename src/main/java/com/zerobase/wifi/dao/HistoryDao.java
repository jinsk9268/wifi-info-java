package com.zerobase.wifi.dao;

import com.zerobase.wifi.db.SQLiteDbConnection;
import com.zerobase.wifi.dto.HistoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            } catch (SQLException e) {
                e.printStackTrace();
            }

            closeDbConnection();
        }
    }

    public List<HistoryDto> selectHistory() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<HistoryDto> historyList = new ArrayList<>();
        final String sql = " SELECT * FROM history ORDER BY id DESC; ";

        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HistoryDto historyDto = new HistoryDto();
                historyDto.setId(resultSet.getInt("id"));
                historyDto.setLongitude(resultSet.getDouble("longitude"));
                historyDto.setLatitude(resultSet.getDouble("latitude"));
                historyDto.setCheckDatetime(resultSet.getString("check_datetime"));

                historyList.add(historyDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            closeDbConnection();
        }

        return historyList;
    }

    public boolean deleteHistoryId(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted = false;

        final String sql = " DELETE FROM history WHERE id = ?; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);

            preparedStatement.executeUpdate();
            connection.commit();
            isDeleted = true;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            closeDbConnection();
        }

        return isDeleted;
    }
}
