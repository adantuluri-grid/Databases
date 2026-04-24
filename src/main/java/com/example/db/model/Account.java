package com.example.db.model;

public class Account {

    private long id;
    private String ownerName;
    private double balance;

    public Account(long id, String ownerName, double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return id + " | " + ownerName + " | " + balance;
    }
}