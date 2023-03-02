package com.zerobase.wifi.dao;

import com.zerobase.wifi.db.SQLiteDbConnection;
import com.zerobase.wifi.dto.PublicWifiDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<PublicWifiDto> selectNearWifi(double latitude, double longitude) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<PublicWifiDto> nearWifiList = new ArrayList<>();
        final String sql = " SELECT round( "
                + " 6371 * ACOS( "
                + " COS(RADIANS(?)) "
                + " * COS(RADIANS(pwi.latitude)) "
                + " * COS(RADIANS(pwi.longitude) - RADIANS(?)) "
                + " + SIN(RADIANS(?)) "
                + " * SIN(RADIANS(pwi.latitude))), 4) AS distance, * "
                + "FROM public_wifi_info pwi ORDER BY distance ASC LIMIT 20; ";

        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            // 공식과 좌표 순서 다시 확인
            preparedStatement.setDouble(1, longitude);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PublicWifiDto publicWifiDto = new PublicWifiDto();
                publicWifiDto.setDistance(resultSet.getDouble("distance"));
                publicWifiDto.setManageNo(resultSet.getString("manage_no"));
                publicWifiDto.setBorough(resultSet.getString("borough"));
                publicWifiDto.setWifiName(resultSet.getString("wifi_name"));
                publicWifiDto.setAddressStreet(resultSet.getString("address_street"));
                publicWifiDto.setAddressDetail(resultSet.getString("address_detail"));
                publicWifiDto.setFloor(resultSet.getString("floor"));
                publicWifiDto.setInstallType(resultSet.getString("install_type"));
                publicWifiDto.setInstallAgency(resultSet.getString("install_agency"));
                publicWifiDto.setServiceText(resultSet.getString("service"));
                publicWifiDto.setNetType(resultSet.getString("net_type"));
                publicWifiDto.setInstallYear(resultSet.getString("install_year"));
                publicWifiDto.setInoutDoor(resultSet.getString("inout_door"));
                publicWifiDto.setWifiConnectionEnv(resultSet.getString("wifi_connection_env"));
                publicWifiDto.setLongitude(resultSet.getDouble("longitude"));
                publicWifiDto.setLatitude(resultSet.getDouble("latitude"));
                publicWifiDto.setWorkDatetime(resultSet.getString("work_datetime"));

                nearWifiList.add(publicWifiDto);
            }

            HistoryDao historyDao = new HistoryDao();
            historyDao.insertHistory(latitude, longitude);
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

        return nearWifiList;
    }

    public PublicWifiDto selectWifiDetail(String manageNo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        PublicWifiDto publicWifiDto = new PublicWifiDto();
        final String sql = " SELECT * FROM public_wifi_info WHERE manage_no = ?; ";

        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, manageNo);

            resultSet = preparedStatement.executeQuery();

            publicWifiDto.setDistance(0.0);
            publicWifiDto.setManageNo(resultSet.getString("manage_no"));
            publicWifiDto.setBorough(resultSet.getString("borough"));
            publicWifiDto.setWifiName(resultSet.getString("wifi_name"));
            publicWifiDto.setAddressStreet(resultSet.getString("address_street"));
            publicWifiDto.setAddressDetail(resultSet.getString("address_detail"));
            publicWifiDto.setFloor(resultSet.getString("floor"));
            publicWifiDto.setInstallType(resultSet.getString("install_type"));
            publicWifiDto.setInstallAgency(resultSet.getString("install_agency"));
            publicWifiDto.setServiceText(resultSet.getString("service"));
            publicWifiDto.setNetType(resultSet.getString("net_type"));
            publicWifiDto.setInstallYear(resultSet.getString("install_year"));
            publicWifiDto.setInoutDoor(resultSet.getString("inout_door"));
            publicWifiDto.setWifiConnectionEnv(resultSet.getString("wifi_connection_env"));
            publicWifiDto.setLongitude(resultSet.getDouble("longitude"));
            publicWifiDto.setLatitude(resultSet.getDouble("latitude"));
            publicWifiDto.setWorkDatetime(resultSet.getString("work_datetime"));
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

        return publicWifiDto;
    }

    public boolean updateWifiAddBookmarkGroup(String manageNo, int groupId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdateIdBookmark = false;

        final String sql = " UPDATE public_wifi_info "
                        + " SET id_bookmark = ?, register_datetime_bookmark = ? WHERE manage_no = ?; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, groupId);
            preparedStatement.setObject(2, LocalDateTime.now());
            preparedStatement.setObject(3, manageNo);
            preparedStatement.executeUpdate();

            connection.commit();

            isUpdateIdBookmark = true;
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

        return isUpdateIdBookmark;
    }

    public Map<String, String> selectDeleteBookmarkWifi(String manageNo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, String> deleteBookmark = new HashMap<>();

        final String sql = " SELECT b.bookmark_name , pwi.wifi_name , pwi.register_datetime_bookmark "
                    + " FROM public_wifi_info pwi "
                    + " INNER JOIN bookmark b ON pwi.id_bookmark = b.id "
                    + " WHERE pwi.manage_no = ?; ";

        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, manageNo);
            resultSet = preparedStatement.executeQuery();

            deleteBookmark.put("bookmarkName", resultSet.getString("bookmark_name"));
            deleteBookmark.put("wifiName", resultSet.getString("wifi_name"));
            deleteBookmark.put("registerDatetime", resultSet.getString("register_datetime_bookmark"));
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

        return deleteBookmark;
    }

    public boolean updateBookmarkDataNull(String manageNo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdateBookmarkDataNull = false;

        final String sql = " UPDATE public_wifi_info "
                    + " SET id_bookmark = NULL , register_datetime_bookmark = NULL "
                    + " WHERE manage_no = ?; ";

        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, manageNo);
            preparedStatement.executeUpdate();

            connection.commit();
            isUpdateBookmarkDataNull = true;
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

        return isUpdateBookmarkDataNull;
    }
}
