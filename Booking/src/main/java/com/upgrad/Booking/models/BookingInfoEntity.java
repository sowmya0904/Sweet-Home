package com.upgrad.Booking.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingInfoEntity {
    private int id;

    private Date fromDate;

    private Date toDate;

    private String aadharNumber;

    private int numOfRooms;

    private String roomNumbers;

    private int roomPrice;


    private int transactionId;


    private Date bookedOn;
}
