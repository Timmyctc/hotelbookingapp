package org.dip.tus.runner;

import org.dip.tus.Customer;
import org.dip.tus.booking.RoomBooking;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Customer conor = new Customer("Conor", LocalDate.of(1991,10,06), false);
        RoomBooking room1 = new RoomBooking(conor,1, LocalDateTime.of(12,1,1,1,1,1)
                ,LocalDateTime.of(13,1,1,1,1));
    }


}