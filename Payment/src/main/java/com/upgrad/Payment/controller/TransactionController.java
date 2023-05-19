package com.upgrad.Payment.controller;


import com.upgrad.Payment.dto.TransactionDTO;
import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.TransactionDetailsEntity;
import com.upgrad.Payment.services.TransactionService;
import com.upgrad.Payment.utils.POJOConvertor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class TransactionController {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/transaction",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> processTransaction( @RequestBody TransactionDTO transactionDto){
        System.out.println(transactionDto+"***************** from payments service controller");
        Transaction transaction=modelMapper.map(transactionDto, Transaction.class);
        Integer processedTransactionId = transactionService.processingTransaction(transaction);
        return new ResponseEntity(processedTransactionId, HttpStatus.CREATED);
        //return processedTransactionId;
    }

    @GetMapping(value = "transaction/{transactionId}")
    public ResponseEntity getTransactionDetails(@PathVariable(value = "transactionId")int transactionId){
        Transaction transactionData = transactionService.getTransactionData(transactionId);
        TransactionDetailsEntity transactionInfoData= POJOConvertor.covertTransactionEntityToTransactionInfoEntity(transactionData);
        return new ResponseEntity(transactionInfoData, HttpStatus.OK);
    }
}
