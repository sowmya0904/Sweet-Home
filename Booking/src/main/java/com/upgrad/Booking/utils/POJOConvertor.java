package com.upgrad.Booking.utils;

import com.upgrad.Booking.entities.Booking;
import com.upgrad.Booking.models.responseentities.BookingInfoEntity;


public class POJOConvertor {

  public static BookingInfoEntity covertBookingEntityToBookingInfoEntity(Booking booking) {
    BookingInfoEntity bookingInfoEntity=new BookingInfoEntity();
    bookingInfoEntity.setId(booking.getBookingId());
    bookingInfoEntity.setBookedOn(booking.getBookedOn());
    bookingInfoEntity.setAadharNumber(booking.getAadharNumber());
    bookingInfoEntity.setFromDate(booking.getFromDate());
    bookingInfoEntity.setToDate(booking.getToDate());
    bookingInfoEntity.setNumOfRooms(booking.getNumOfRooms());
    bookingInfoEntity.setRoomNumbers(booking.getRoomNumbers());
    bookingInfoEntity.setRoomPrice(booking.getRoomPrice());
    bookingInfoEntity.setTransactionId(booking.getTransactionId());
    return bookingInfoEntity;
  }
}
