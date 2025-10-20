package com.tianshi.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC 工具类
 * 负责数据库连接的获取和资源的释放
 */
public class JDBCUtil {

    // 数据库连接参数
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bank?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123123";

    // 静态代码块，在类加载时执行一次，用于加载数据库驱动
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            // 如果驱动找不到，程序就无法继续，这里直接抛出运行时异常
            throw new RuntimeException("MySQL驱动加载失败", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection 数据库连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 释放资源（关闭 Connection, PreparedStatement, ResultSet）
     *
     * @param conn Connection对象
     * @param ps   PreparedStatement对象
     * @param rs   ResultSet对象
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源（关闭 Connection, PreparedStatement），用于增、删、改操作
     *
     * @param conn Connection对象
     * @param ps   PreparedStatement对象
     */
    public static void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null);
    }
}