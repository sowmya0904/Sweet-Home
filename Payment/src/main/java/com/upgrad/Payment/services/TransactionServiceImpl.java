package com.upgrad.Payment.services;


import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.TransactionInfoEntity;
import com.upgrad.Payment.repository.TransactionRepo;
import com.upgrad.Payment.utils.POJOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepo transactionRepo;


    @Override
    public Integer processingTransaction(Transaction transaction) {
        Transaction transactionProcessed=transactionRepo.save(transaction);
        return transactionProcessed.getTransactionId();
    }

    @Override
    public Transaction getTransactionData(int transactionId) {
       Transaction transactionData=transactionRepo.findById(transactionId).get();
       return transactionData;
    }
}
