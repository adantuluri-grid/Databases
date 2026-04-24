package com.example.db.demo;

import com.example.db.repository.AccountRepository;
import com.example.db.service.TransferService;

public class TransactionDemoMain {

    public static void main(String[] args) {

        AccountRepository repo =
                new AccountRepository();

        TransferService service =
                new TransferService();

        System.out.println("INITIAL STATE");
        repo.printAll();

        System.out.println("\nWITHOUT TRANSACTION");
        service.transferWithoutTransaction(
                1, 2, 300
        );
        repo.printAll();

        repo.resetBalances();

        System.out.println("\nWITH TRANSACTION");
        service.transferWithTransaction(
                1, 2, 300
        );
        repo.printAll();
    }
}