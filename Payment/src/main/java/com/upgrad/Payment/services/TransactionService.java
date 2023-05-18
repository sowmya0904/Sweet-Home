package com.upgrad.Payment.services;

import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.TransactionInfoEntity;

public interface TransactionService {

    Integer processingTransaction(Transaction transaction);
    Transaction getTransactionData(int transactionId);

}
