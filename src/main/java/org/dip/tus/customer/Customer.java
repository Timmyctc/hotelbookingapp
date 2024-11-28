package org.dip.tus.customer;

import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDate;

/**
 * Represents a customer with a name and date of birth.
 */
public record Customer(String name, LocalDate dateOfBirth) {

    /**
     * Constructs a new {@code Customer} record.
     *
     * @param name        the name of the customer.
     * @param dateOfBirth the date of birth of the customer.
     * @throws IllegalArgumentException if the name is blank or dateOfBirth is null.
     */
    public Customer {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of Birth cannot be null.");
        }
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
