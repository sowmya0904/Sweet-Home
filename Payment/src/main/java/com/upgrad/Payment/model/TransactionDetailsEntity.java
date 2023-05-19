package com.upgrad.Payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class TransactionDetailsEntity {

    private int id;

    private String paymentMode;

    private int bookingId;

    private String upiId;

    private String cardNumber;
}
