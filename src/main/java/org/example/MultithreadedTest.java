package org.example;

import db.DatabaseUtil;
import db.HikariDataSourceProvider;
import db.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.util.concurrent.*;

public class MultithreadedTest {

    public static void main(String[] args) throws Exception {


        DataSource ds = new SingleConnectionDataSource(
                "jdbc:postgresql://localhost:5475/consentrajdbc",
                "admin",
                "admin"
        );



        DatabaseUtil db = new DatabaseUtil(ds);

        int threads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        long start = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    db.execute("SELECT pg_sleep(2)");
                    System.out.println(Thread.currentThread().getName() + " done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long end = System.currentTimeMillis();

        System.out.println("Total Time: " + (end - start) + " ms");
    }
}