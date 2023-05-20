package com.upgrad.Booking.service;

import com.upgrad.Booking.dto.TransactionDTO;
import com.upgrad.Booking.entities.Booking;
import com.upgrad.Booking.entities.Transaction;
import com.upgrad.Booking.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BookingServiceImpl implements BookingService{

    //URL of service to make a synchronous call this url is configured in application.properties and used here
    @Value("${transactionApp.url}")
    private String transactionAppUrl;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public Booking checkRoomAvailability(Booking booking) {

    //Logic to calculate the no of days between the dates given
        long noOfDays=0;
        Date datefrom = booking.getFromDate();
        Date dateto = booking.getToDate();
        noOfDays = dateto.getTime() - datefrom.getTime();
        long diffDays = noOfDays / (24 * 60 * 60 * 1000);

        Booking saveBookingDetails = Booking.builder()
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .numOfRooms(booking.getNumOfRooms())
                .aadharNumber(booking.getAadharNumber())
                .roomNumbers(getRandomNumbers(booking.getNumOfRooms()))
                .roomPrice((int) calculateRoomPrice((int)diffDays,booking.getNumOfRooms()))
                .bookedOn(new Date())
                .build();
        bookingRepo.save(saveBookingDetails);
        return saveBookingDetails;
    }

    //Logic to check if the passed bookingId part of payment is valid or no
    @Override
    public Boolean validateBookingId(int bookingId) {
        if(bookingRepo.findById(bookingId).isEmpty()){
            return true;
        }
        return false;
    }


    //Logic where we make synchronous call to Payment service via RestTemplate
    @Override
    public Booking transactionSuccess(TransactionDTO transaction) {
        int transactionId = restTemplate.postForObject(transactionAppUrl,transaction,Integer.class);

        Booking paymentSuccess= bookingRepo.findById(transaction.getBookingId()).get();
        paymentSuccess.setTransactionId(transactionId);
        bookingRepo.save(paymentSuccess);
        String message = "Booking confirmed for user with aadhaar number: "
                + paymentSuccess.getAadharNumber()
                +    "    |    "
                + "Here are the booking details:    " + paymentSuccess.toString();
        System.out.println(message);
        return paymentSuccess;
    }

    //Logic to claculate price of particular No of rooms and No of Days
    public long calculateRoomPrice(int noOfDays,int noOfRooms){
       long roomPrice = 1000 * (noOfRooms * noOfDays);
        return roomPrice;
    }

    //Logic to generate room numbers
    public static String getRandomNumbers(int count){
        Random rand = new Random();
        StringBuffer rooms= new StringBuffer();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        for(int i=0;i<numberList.size()-1;i++){
            rooms.append(numberList.get(i)+",");
        }
        rooms.append(numberList.get(numberList.size()-1));


        return rooms.toString();
    }

    //Logic to get data of a bookingId from Database
    @Override
    public Booking getBookingData(int bookingId) {
        Booking bookingDetails=bookingRepo.findById(bookingId).get();
        return bookingDetails;
    }


}
