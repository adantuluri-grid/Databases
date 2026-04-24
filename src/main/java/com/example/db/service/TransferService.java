package com.example.db.service;

import com.example.db.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransferService {

    public void transferWithoutTransaction(
            long fromId,
            long toId,
            double amount
    ) {

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement deduct =
                    conn.prepareStatement(
                            "UPDATE accounts SET balance = balance - ? WHERE id = ?"
                    );

            deduct.setDouble(1, amount);
            deduct.setLong(2, fromId);
            deduct.executeUpdate();

            throw new RuntimeException(
                    "System crashed after deducting money!"
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferWithTransaction(
            long fromId,
            long toId,
            double amount
    ) {

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement deduct =
                    conn.prepareStatement(
                            "UPDATE accounts SET balance = balance - ? WHERE id = ?"
                    );

            deduct.setDouble(1, amount);
            deduct.setLong(2, fromId);
            deduct.executeUpdate();

            throw new RuntimeException(
                    "System crashed after deducting money!"
            );

        } catch (Exception e) {

            System.out.println(e.getMessage());

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ignored) {
            }

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}