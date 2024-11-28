package org.dip.tus.exception;

/**
 * Exception thrown when invalid booking dates are provided.
 */

public class BookingDateArgumentException extends Exception {

    public BookingDateArgumentException() {
        super();
    }

    public BookingDateArgumentException(String message) {
        super(message);
    }
}

