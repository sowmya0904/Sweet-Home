package com.upgrad.Payment.controller;


import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.TransactionInfoEntity;
import com.upgrad.Payment.services.TransactionService;
import com.upgrad.Payment.utils.POJOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/transaction",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer processTransaction( @RequestBody Transaction transaction){
        Integer processedTransactionId = transactionService.processingTransaction(transaction);
        return processedTransactionId;
    }

    @GetMapping(value = "transaction/{transactionId}")
    public ResponseEntity getTransactionDetails(@PathVariable(value = "transactionId")int transactionId){
        Transaction transactionData = transactionService.getTransactionData(transactionId);
        TransactionInfoEntity transactionInfoData= POJOConvertor.covertTransactionEntityToTransactionInfoEntity(transactionData);
        return new ResponseEntity(transactionInfoData, HttpStatus.OK);
    }
}
