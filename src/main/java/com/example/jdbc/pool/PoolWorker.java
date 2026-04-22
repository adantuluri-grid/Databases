package com.example.jdbc.pool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class PoolWorker implements Runnable {

    private final DataSource dataSource;
    private final int id;

    public PoolWorker(DataSource dataSource, int id) {
        this.dataSource = dataSource;
        this.id = id;
    }

    @Override
    public void run() {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            System.out.println("Thread " + id + " started");

            stmt.executeQuery("SELECT pg_sleep(3)");

            System.out.println("Thread " + id + " finished");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}