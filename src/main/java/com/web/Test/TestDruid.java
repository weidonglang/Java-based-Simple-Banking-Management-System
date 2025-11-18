package com.web.Test;

import com.web.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用 DruidUtil 测试查询所有用户信息
 */
public class TestDruid {

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. 从 Druid 连接池获取连接
            conn = DruidUtil.getConnection();

            // 2. SQL：查询所有用户
            String sql = "SELECT id, username, password FROM user";

            // 3. 预编译 SQL
            ps = conn.prepareStatement(sql);

            // 4. 执行查询
            rs = ps.executeQuery();

            // 5. 遍历结果集
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("id=" + id
                        + ", username=" + username
                        + ", password=" + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭资源（归还连接给连接池）
            DruidUtil.close(conn, ps, rs);
        }
    }
}
