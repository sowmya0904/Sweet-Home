package com.upgrad.Booking.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;

    @Column(nullable = true)
    private Date fromDate;

    @Column(nullable = true)
    private Date toDate;

    @Column(nullable = true)
    private String aadharNumber;

    private int numOfRooms;

    private String roomNumbers;

    @Column(nullable = false)
    private int roomPrice;

    @Column(columnDefinition = "integer default 0")
    private int transactionId;

    @Column(nullable = true)
    private Date bookedOn;

}
