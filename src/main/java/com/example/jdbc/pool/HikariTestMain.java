package com.example.jdbc.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariTestMain {

    public static void main(String[] args) throws Exception {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
                "jdbc:postgresql://localhost:5465/jdbcdb"
        );
        config.setUsername("admin");
        config.setPassword("admin");

        config.setMaximumPoolSize(5);

        HikariDataSource ds =
                new HikariDataSource(config);

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            threads[i] =
                    new Thread(new PoolWorker(ds, i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("Total Time: "
                + (end - start) / 1000.0 + " sec");

        ds.close();
    }
}