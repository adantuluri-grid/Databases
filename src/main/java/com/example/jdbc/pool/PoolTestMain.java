package com.example.jdbc.pool;

import javax.sql.DataSource;

public class PoolTestMain {

    public static void main(String[] args) throws Exception {

        DataSource ds = new CustomDataSource();

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new PoolWorker(ds, i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("Total Time: "
                + (end - start) / 1000.0 + " sec");
    }
}