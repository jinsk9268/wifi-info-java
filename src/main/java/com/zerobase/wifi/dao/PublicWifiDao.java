package com.zerobase.wifi.dao;

import com.zerobase.wifi.db.SQLiteDbConnection;
import com.zerobase.wifi.dto.PublicWifiDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PublicWifiDao extends SQLiteDbConnection {
    public boolean insertTotalPublicWifiData(List<PublicWifiDto> list) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isTotalDataInsert = true;

        final String sql = " INSERT INTO public_wifi_info " +
                " (manage_no, borough, wifi_name, address_street, address_detail, floor, install_type, " +
                " install_agency, service, net_type, install_year, inout_door, wifi_connection_env," +
                " longitude, latitude, work_datetime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (PublicWifiDto wifi: list) {
                preparedStatement.setObject(1, wifi.getManageNo());
                preparedStatement.setObject(2, wifi.getBorough());
                preparedStatement.setObject(3, wifi.getWifiName());
                preparedStatement.setObject(4, wifi.getAddressStreet());
                preparedStatement.setObject(5, wifi.getAddressDetail());
                preparedStatement.setObject(6, wifi.getFloor());
                preparedStatement.setObject(7, wifi.getInstallType());
                preparedStatement.setObject(8, wifi.getInstallAgency());
                preparedStatement.setObject(9, wifi.getServiceText());
                preparedStatement.setObject(10, wifi.getNetType());
                preparedStatement.setObject(11, wifi.getInstallYear());
                preparedStatement.setObject(12, wifi.getInoutDoor());
                preparedStatement.setObject(13, wifi.getWifiConnectionEnv());
                preparedStatement.setObject(14, wifi.getLongitude());
                preparedStatement.setObject(15, wifi.getLatitude());
                preparedStatement.setObject(16, wifi.getWorkDatetime());

                preparedStatement.executeUpdate();
            }

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

            isTotalDataInsert = false;
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

        return isTotalDataInsert;
    }

    public int selectTotalCount() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int totalCount = 0;
        final String sql = " SELECT COUNT(*) as totalCount from public_wifi_info; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            totalCount = Integer.parseInt(resultSet.getString("totalCount"));
        } catch (SQLException e) {
            e.printStackTrace();
            totalCount = -1; // error
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            closeDbConnection();
        }

        return totalCount;
    }

    public boolean deleteTotalPublicWifi() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isTotalDataDelete = true;

        final String sql = " DELETE FROM public_wifi_info; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            isTotalDataDelete = false;
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

        return isTotalDataDelete;
    }
}
