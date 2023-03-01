package com.zerobase.wifi.dao;

import com.zerobase.wifi.db.SQLiteDbConnection;
import com.zerobase.wifi.dto.BookmarkDto;
import com.zerobase.wifi.dto.PublicWifiDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<BookmarkDto> selectBookmarkGroup() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BookmarkDto> bookmarkGroupList = new ArrayList<>();
        final String sql = " SELECT * FROM bookmark ORDER BY bookmark_order; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookmarkDto bookmarkDto = new BookmarkDto();
                bookmarkDto.setId(resultSet.getInt("id"));
                bookmarkDto.setBookmarkName(resultSet.getString("bookmark_name"));
                bookmarkDto.setBookmarkOrder(resultSet.getInt("bookmark_order"));
                bookmarkDto.setRegisterDatetime(resultSet.getString("register_datetime"));
                bookmarkDto.setModificationDatetime(resultSet.getString("modification_datetime"));

                bookmarkGroupList.add(bookmarkDto);
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

        return bookmarkGroupList;
    }

    public BookmarkDto selectBookmarkGroupFromId(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BookmarkDto bookmarkDto = new BookmarkDto();
        final String sql = " SELECT bookmark_name, bookmark_order "
                        + " FROM bookmark WHERE id = ?; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            resultSet = preparedStatement.executeQuery();

            bookmarkDto.setBookmarkName(resultSet.getString("bookmark_name"));
            bookmarkDto.setBookmarkOrder(resultSet.getInt("bookmark_order"));
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

        return bookmarkDto;
    }

    public boolean updateBookmarkGroup(int id, String editName, int editOrder) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isBookmarkGroupUpdate = false;

        final String sql = " UPDATE bookmark "
                        + " SET bookmark_name = ?, bookmark_order = ?, modification_datetime = ? "
                        + " WHERE id = ?; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, editName);
            preparedStatement.setObject(2, editOrder);
            preparedStatement.setObject(3, LocalDateTime.now());
            preparedStatement.setObject(4, id);
            preparedStatement.executeUpdate();

            connection.commit();

            isBookmarkGroupUpdate = true;
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

        return isBookmarkGroupUpdate;
    }

    public boolean deleteBookmarkGroupFromId(int deleteId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted = false;

        final String sql = " DELETE from bookmark WHERE id = ?; ";
        try {
            connection = getDbConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, deleteId);

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
