package com.web.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Druid 连接池工具类：
 *  - 静态初始化 DataSource
 *  - 提供 getConnection()
 *  - 提供关闭资源的便捷方法
 *
 * 需要在 src/main/resources 下提供 db.properties 配置文件。
 */
public class DruidUtil {

    private static DataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            // db.properties 放在 src/main/resources 根目录
            try (InputStream in = DruidUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties")) {

                if (in == null) {
                    throw new RuntimeException("未找到 db.properties，请确认放在 src/main/resources 下");
                }
                props.load(in);
            }

            dataSource = DruidDataSourceFactory.createDataSource(props);
            System.out.println("[DruidUtil] DataSource 初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /** 从 Druid 连接池获取连接 */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /** 关闭资源（查询用） */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException ignored) {}
        }
        if (ps != null) {
            try { ps.close(); } catch (SQLException ignored) {}
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    /** 关闭资源（增删改用） */
    public static void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null);
    }
}
