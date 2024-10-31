package org.dip.tus;

import java.time.LocalDate;

public class Customer {

    private String name;
    private LocalDate dateOfBirth;
    private boolean isPlusMember;
    //Can be used in conditional logic for calculating customers fees for bookings etc later.

    public Customer(String name, LocalDate dob, boolean isPlusMember) {
        this.name = name;
        this.dateOfBirth = dob;
        this.isPlusMember = isPlusMember;
    }
}
