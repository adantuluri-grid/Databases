package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PreparedStatementDemo {

    public static void login(String username, String password) {

        String sql =
                "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login SUCCESS using PreparedStatement");
            } else {
                System.out.println("Login FAILED using PreparedStatement");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}