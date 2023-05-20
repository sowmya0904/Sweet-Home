package com.upgrad.Payment.controller;


import com.upgrad.Payment.dto.TransactionDTO;
import com.upgrad.Payment.entities.Transaction;
import com.upgrad.Payment.model.InvalidTransactionDetailsException;
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

    /*
    This is a Post HTTP method when restTemplate makes a Synchronous call it points to this Api end point
    it receives the transaction data and saves it and generates a transaction id and sends it back to booking service
    If this end point is called manually without intervension of Booking service it just sends transaction id in
    response
     */
    @PostMapping(value = "/transaction",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> processTransaction( @RequestBody TransactionDTO transactionDto) {
        String paymentMode = transactionDto.getPaymentMode();
        if (paymentMode.equals("UPI") || paymentMode.equals("CARD")) {
            Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
            Integer processedTransactionId = transactionService.processingTransaction(transaction);
            return new ResponseEntity(processedTransactionId, HttpStatus.CREATED);
        } else {
            InvalidTransactionDetailsException invalidCardDetails = InvalidTransactionDetailsException.builder()
                    .message("Invalid mode of payment")
                    .statusCode("400")
                    .build();
            return new ResponseEntity(invalidCardDetails, HttpStatus.BAD_REQUEST);
        }
    }

    /*
    This is a GET HTTP method you need to pass transactionId as part of path variable then it fetches the data regarding
    the transaction ID and send the same as response, if id is not valid, message is displayed as result
     */
    @GetMapping(value = "transaction/{transactionId}")
    public ResponseEntity getTransactionDetails(@PathVariable(value = "transactionId")int transactionId) {
        try {
            Transaction transactionData = transactionService.getTransactionData(transactionId);
            TransactionDetailsEntity transactionInfoData = POJOConvertor.covertTransactionEntityToTransactionInfoEntity(transactionData);
            return new ResponseEntity(transactionInfoData, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity("Transaction Id is Invalid please pass valid id", HttpStatus.NOT_FOUND);
        }
    }
}
