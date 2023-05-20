package com.upgrad.Booking.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor@NoArgsConstructor@Builder
public class BookingDTO {
    private int bookingId;
    private Date fromDate;

    private Date toDate;


    private String aadharNumber;

    private int numOfRooms;

}
