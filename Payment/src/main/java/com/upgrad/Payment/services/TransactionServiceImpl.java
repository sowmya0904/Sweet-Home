package com.upgrad.Payment.services;


import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepo transactionRepo;

    //Logic to save transaction data to DataBase
    @Override
    public Integer processingTransaction(Transaction transaction) {
        Transaction transactionProcessed=transactionRepo.save(transaction);
        return transactionProcessed.getTransactionId();
    }

    //Logic to fetch data for a particular TransactionId from DataBase
    @Override
    public Transaction getTransactionData(int transactionId) {
       Transaction transactionData=transactionRepo.findById(transactionId).get();
       return transactionData;
    }
}
