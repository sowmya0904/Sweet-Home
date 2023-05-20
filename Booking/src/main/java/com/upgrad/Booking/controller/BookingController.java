package com.upgrad.Booking.controller;

import com.upgrad.Booking.dto.BookingDTO;
import com.upgrad.Booking.dto.TransactionDTO;
import com.upgrad.Booking.entities.Booking;
import com.upgrad.Booking.models.BookingInfoEntity;
import com.upgrad.Booking.models.InvalidTransactionDetailsException;
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

    /*
    This is a post call where user sends basic data like todate and from date and no of rooms, and as a result
    user gets info about the price and room number with extra info too
     */
    @PostMapping(value = "/booking",consumes = MediaType.APPLICATION_JSON_VALUE ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBookingDetails(@RequestBody BookingDTO bookingDTO){
        Booking sendBookingdeatils= modelMapper.map(bookingDTO, Booking.class);
        Booking resultDeatils= bookingService.checkRoomAvailability(sendBookingdeatils);
        BookingInfoEntity resultBookingInfo= POJOConvertor.covertBookingEntityToBookingInfoEntity(resultDeatils);

        return new ResponseEntity(resultBookingInfo, HttpStatus.CREATED);
    }

    /*
    This is a post call where user is Ok with the room numbers and price and he is booking the rooms and send data
    related to paymnets such as payment mode,upi id, card number and the booking id as a result he will get the
    complete info of the booking along with transaction id updated.

    We have hard stop rules where user have to give payment mode either UPI or CARD and also Bokking id needs to be valid
     */
    @PostMapping(value = "/booking/{bookingId}/transaction",consumes = MediaType.APPLICATION_JSON_VALUE
    ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity makePaymentsForRooms(@PathVariable("bookingId") int bookingId,@RequestBody TransactionDTO transactionDTO) {
        String paymentMode = transactionDTO.getPaymentMode();

        if (paymentMode.equals("UPI") || paymentMode.equals("CARD")) {

            Boolean bookingIdValidated = bookingService.validateBookingId(bookingId);
            if (bookingIdValidated == true) {
                InvalidTransactionDetailsException invalidBookingId = InvalidTransactionDetailsException.builder()
                        .message("Invalid Booking Id")
                        .statusCode("400")
                        .build();
                return new ResponseEntity(invalidBookingId, HttpStatus.BAD_REQUEST);

            }
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

    //Logic to get booking details of a specific bookingId
    @GetMapping(value = "/booking/{bookingId}")
    public ResponseEntity getBookingData(@PathVariable (value = "bookingId") int bookingId){
        try {
            Booking bookingDataForBookingId = bookingService.getBookingData(bookingId);
            BookingInfoEntity bookingData = POJOConvertor.covertBookingEntityToBookingInfoEntity(bookingDataForBookingId);
            return new ResponseEntity(bookingData, HttpStatus.OK);
        }catch (Exception e){
                return new ResponseEntity("Booking Id is Invalid please pass valid id", HttpStatus.NOT_FOUND);
            }
    }

}
