package com.example.jdbc;

public class Main {

    public static void main(String[] args) {

        DatabaseSetup.init();

        System.out.println("\n--- Normal Login ---");
        StatementDemo.login("admin", "admin123");
        PreparedStatementDemo.login("admin", "admin123");

        System.out.println("\n--- SQL Injection Attack ---");

        String injectedUsername = "admin' --";
        String injectedPassword = "anything";

        StatementDemo.login(injectedUsername, injectedPassword);
        PreparedStatementDemo.login(injectedUsername, injectedPassword);
    }
}