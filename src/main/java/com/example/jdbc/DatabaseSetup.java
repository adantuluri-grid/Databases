package com.example.jdbc;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void init() {

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(100),
                    password VARCHAR(100)
                )
            """);

            stmt.execute("DELETE FROM users");

            stmt.execute("""
                INSERT INTO users(username, password)
                VALUES
                ('admin', 'admin123'),
                ('john', 'john123'),
                ('alice', 'alice123')
            """);

            System.out.println("Database initialized.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}