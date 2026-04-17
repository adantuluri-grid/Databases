package com.example.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatementDemo {

    public static void login(String username, String password) {

        String sql =
                "SELECT * FROM users WHERE username='"
                        + username
                        + "' AND password='"
                        + password + "'";

        System.out.println("Executing: " + sql);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("Login SUCCESS using Statement");
            } else {
                System.out.println("Login FAILED using Statement");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}