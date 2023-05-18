package com.upgrad.Booking.service;

import com.upgrad.Booking.entities.Booking;
import com.upgrad.Booking.entities.Transaction;

public interface BookingService {

    Booking checkRoomAvailability(Booking booking);

    Boolean validateBookingId(int bookingId);

    Booking transactionSuccess(Transaction transaction);
}
