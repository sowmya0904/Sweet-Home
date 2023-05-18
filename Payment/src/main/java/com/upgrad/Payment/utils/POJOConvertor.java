package com.upgrad.Payment.utils;

import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.TransactionInfoEntity;


public class POJOConvertor {

  public static TransactionInfoEntity covertTransactionEntityToTransactionInfoEntity(Transaction transaction) {
    TransactionInfoEntity transactionInfoEntity=TransactionInfoEntity.builder()
            .id(transaction.getTransactionId())
            .upiId(transaction.getUpiId())
            .paymentMode(transaction.getPaymentMode())
            .bookingId(transaction.getBookingId())
            .cardNumber(transaction.getCardNumber())
            .build();

    return transactionInfoEntity;
  }
}
