package com.example.db.repository;

import com.example.db.config.DBConnection;
import com.example.db.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountRepository {

    public void printAll() {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(
                             "SELECT * FROM accounts ORDER BY id");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account account = new Account(
                        rs.getLong("id"),
                        rs.getString("owner_name"),
                        rs.getDouble("balance")
                );

                System.out.println(account);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void resetBalances() {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(
                             """
                             UPDATE accounts
                             SET balance =
                             CASE
                               WHEN id = 1 THEN 1000
                               WHEN id = 2 THEN 500
                               WHEN id = 3 THEN 700
                             END
                             """
                     )) {

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}