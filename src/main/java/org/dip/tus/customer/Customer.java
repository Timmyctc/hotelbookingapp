package org.dip.tus.customer;

import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private final String name;
    private final LocalDate dateOfBirth;

    protected Customer(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
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
        return String.format(
                ConsoleColour.CYAN + "Customer Details:\n" + ConsoleColour.RESET +
                        ConsoleColour.GREEN + "Name: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.YELLOW + "Date of Birth: " + ConsoleColour.RESET + "%s\n",
                name,
                dateOfBirth
        );
    }

}
