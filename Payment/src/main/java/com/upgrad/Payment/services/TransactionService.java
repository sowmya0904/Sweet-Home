package com.upgrad.Payment.services;

import com.upgrad.Payment.entities.Transaction;

public interface TransactionService {

    Integer processingTransaction(Transaction transaction);
    Transaction getTransactionData(int transactionId);

}
