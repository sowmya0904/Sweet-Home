package com.upgrad.Booking.service;

import com.upgrad.Booking.dto.TransactionDTO;
import com.upgrad.Booking.entities.Booking;

public interface BookingService {

    Booking checkRoomAvailability(Booking booking);

    Boolean validateBookingId(int bookingId);

    Booking transactionSuccess(TransactionDTO transaction);

    Booking getBookingData(int bookingId);
}
