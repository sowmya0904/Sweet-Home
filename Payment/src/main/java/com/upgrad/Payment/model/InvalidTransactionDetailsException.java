package com.upgrad.Payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class InvalidTransactionDetailsException {

    private String message;
    private String statusCode;
}
