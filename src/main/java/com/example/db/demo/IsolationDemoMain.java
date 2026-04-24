package com.example.db.demo;

import com.example.db.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IsolationDemoMain {

    public static void main(String[] args) throws Exception {

        resetBalance();

        System.out.println("DEFAULT LEVEL:");
        runWithdrawTest(Connection.TRANSACTION_READ_COMMITTED);

        resetBalance();

        System.out.println("\nSERIALIZABLE:");
        runWithdrawTest(Connection.TRANSACTION_SERIALIZABLE);
    }

    private static void runWithdrawTest(int level)
            throws Exception {

        Thread t1 = new Thread(() ->
                withdraw(1, 700, level, "T1"));

        Thread t2 = new Thread(() ->
                withdraw(1, 700, level, "T2"));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        printBalance();
    }

    private static void withdraw(
            long id,
            double amount,
            int level,
            String name
    ) {

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(level);

            PreparedStatement ps =
                    conn.prepareStatement(
                            "SELECT balance FROM accounts WHERE id = ?"
                    );

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            rs.next();

            double balance = rs.getDouble(1);

            Thread.sleep(2000);

            if (balance >= amount) {

                PreparedStatement update =
                        conn.prepareStatement(
                                """
                                UPDATE accounts
                                SET balance = balance - ?
                                WHERE id = ?
                                """
                        );

                update.setDouble(1, amount);
                update.setLong(2, id);
                update.executeUpdate();

                conn.commit();

                System.out.println(name + " success");

            } else {
                conn.rollback();
                System.out.println(name + " insufficient funds");
            }

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {
            }

            System.out.println(name + " failed");

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }
    }

    private static void resetBalance()
            throws Exception {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             """
                             UPDATE accounts
                             SET balance = 1000
                             WHERE id = 1
                             """
                     )) {

            ps.executeUpdate();
        }
    }

    private static void printBalance()
            throws Exception {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             """
                             SELECT balance
                             FROM accounts
                             WHERE id = 1
                             """
                     );

             ResultSet rs = ps.executeQuery()) {

            rs.next();

            System.out.println(
                    "Final Balance = "
                            + rs.getDouble(1)
            );
        }
    }
}