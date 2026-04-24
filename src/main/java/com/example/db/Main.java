package com.example.db;

import com.example.db.config.DBConnection;

public class Main {

    public static void main(String[] args) {

        try {
            boolean valid =
                    DBConnection.getConnection().isValid(3);

            System.out.println("Database Connected: " + valid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}