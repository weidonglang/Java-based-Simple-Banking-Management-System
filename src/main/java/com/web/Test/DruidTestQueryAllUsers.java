package com.web.Test;

import com.web.entity.User;
import com.web.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DruidTestQueryAllUsers {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password FROM user";

        try (Connection conn = DruidUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                users.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=== 使用 Druid 查询到的全部用户 ===");
        for (User u : users) {
            System.out.println(u);
        }
    }
}
