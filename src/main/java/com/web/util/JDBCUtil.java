package com.web.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC 工具类，对外统一入口，内部基于 Druid 连接池。
 */
public class JDBCUtil {

    /** 获取数据库连接：实际从 Druid 连接池中取 */
    public static Connection getConnection() throws SQLException {
        return DruidUtil.getConnection();
    }

    /** 关闭资源（查询用） */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        DruidUtil.close(conn, ps, rs);
    }

    /** 关闭资源（增删改用） */
    public static void close(Connection conn, PreparedStatement ps) {
        DruidUtil.close(conn, ps);
    }
}
