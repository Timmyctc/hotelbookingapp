package org.dip.tus.customer;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private final String name;
    private final LocalDate dateOfBirth;
    private final boolean isPlusMember;

    protected Customer(String name, LocalDate dateOfBirth, boolean isPlusMember) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.isPlusMember = isPlusMember;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isPlusMember() {
        return isPlusMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return name.equalsIgnoreCase(customer.name) && dateOfBirth.equals(customer.dateOfBirth);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", isPlusMember=" + isPlusMember +
                '}';
    }
}
