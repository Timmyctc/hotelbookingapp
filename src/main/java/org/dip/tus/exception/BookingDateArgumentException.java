package org.dip.tus.exception;

public class BookingDateArgumentException extends Exception {

    // Default constructor with no message
    public BookingDateArgumentException() {
        super();
    }

    // Constructor with a custom message
    public BookingDateArgumentException(String message) {
        super(message);
    }
}

