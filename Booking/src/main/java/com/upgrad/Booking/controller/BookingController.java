package com.upgrad.Booking.controller;

import com.upgrad.Booking.dto.BookingDTO;
import com.upgrad.Booking.dto.TransactionDTO;
import com.upgrad.Booking.entities.Booking;
import com.upgrad.Booking.entities.Transaction;
import com.upgrad.Booking.models.responseentities.BookingInfoEntity;
import com.upgrad.Booking.models.responseentities.InvalidTransactionDetailsException;
import com.upgrad.Booking.service.BookingService;
import com.upgrad.Booking.utils.POJOConvertor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hotel")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(value = "/booking",consumes = MediaType.APPLICATION_JSON_VALUE ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBookingDetails(@RequestBody BookingDTO bookingDTO){
        Booking sendBookingdeatils= modelMapper.map(bookingDTO, Booking.class);
        Booking resultDeatils= bookingService.checkRoomAvailability(sendBookingdeatils);
        BookingInfoEntity resultBookingInfo= POJOConvertor.covertBookingEntityToBookingInfoEntity(resultDeatils);

        return new ResponseEntity(resultBookingInfo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/{bookingId}/transaction",consumes = MediaType.APPLICATION_JSON_VALUE
    ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity makePaymentsForRooms(@PathVariable("bookingId") int bookingId,@RequestBody TransactionDTO transactionDTO) {
        String paymentMode = transactionDTO.getPaymentMode();

        if (paymentMode.equals("UPI") || paymentMode.equals("CARD")) {

            Boolean bookingIdValidated = bookingService.validateBookingId(bookingId);
            if (bookingIdValidated == true) {
                InvalidTransactionDetailsException invalidBookingId = InvalidTransactionDetailsException.builder()
                        .message(" Invalid Booking Id ")
                        .statusCode("400")
                        .build();
                return new ResponseEntity(invalidBookingId, HttpStatus.BAD_REQUEST);

            }
           // Transaction transactionToBeMade = modelMapper.map(transactionDTO, Transaction.class);
            Booking transactionSucess = bookingService.transactionSuccess(transactionDTO);
            BookingInfoEntity roomsBooked = POJOConvertor.covertBookingEntityToBookingInfoEntity(transactionSucess);
            return new ResponseEntity(roomsBooked, HttpStatus.CREATED);
        } else {
            InvalidTransactionDetailsException invalidCardDetails = InvalidTransactionDetailsException.builder()
                    .message("Invalid mode of payment")
                    .statusCode("400")
                    .build();
            return new ResponseEntity(invalidCardDetails, HttpStatus.BAD_REQUEST);
        }
    }
}
