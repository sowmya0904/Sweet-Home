package com.upgrad.Payment.utils;

import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.TransactionDetailsEntity;


public class POJOConvertor {

  public static TransactionDetailsEntity covertTransactionEntityToTransactionInfoEntity(Transaction transaction) {
    TransactionDetailsEntity transactionDetailsEntity = TransactionDetailsEntity.builder()
            .id(transaction.getTransactionId())
            .upiId(transaction.getUpiId())
            .paymentMode(transaction.getPaymentMode())
            .bookingId(transaction.getBookingId())
            .cardNumber(transaction.getCardNumber())
            .build();

    return transactionDetailsEntity;
  }
}
