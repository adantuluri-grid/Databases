package org.example;

import dao.LogDao;
import db.DatabaseUtil;
import db.HikariDataSourceProvider;
import db.SingleConnectionDataSource;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {

        DataSource ds = HikariDataSourceProvider.create();

        DatabaseUtil db = new DatabaseUtil(ds);

        LogDao logDao = new LogDao(db);

        int next = logDao.nextIndex(1);
        logDao.insert(1, 1, next, "SET z=30");

        System.out.println(logDao.findByNode(1));
    }
}