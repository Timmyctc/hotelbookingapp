package org.dip.tus.util;

import org.dip.tus.room.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

    /**
    * Utility class to get various inputs from user
    */
public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static String parseString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static RoomType parseRoomEnum(String prompt) {
        RoomType roomType = null;
        while (roomType == null) {
            System.out.print(prompt);
            try {
                roomType = RoomType.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e){
                System.out.println("Invalid input, Try Again.");
            }
        }
        return roomType;
    }

    public static int parseInt(String prompt) {
        Integer value = null;
        while (value == null || value < 0) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
        return value;
    }

    public static LocalDate parseDate(String prompt) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            try {
                date = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    public static LocalDateTime parseDateTime(String prompt) {
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            System.out.print(prompt);
            try {
                dateTime = LocalDateTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date-time format. Please use YYYY-MM-DDTHH:MM.");
            }
        }
        return dateTime;
    }

    public static LocalDate parseDateOfBirth(String prompt) {
        LocalDate date = null;
        while (date == null || date.isAfter(LocalDate.now())) {
            System.out.print(prompt);
            try {
                date = LocalDate.parse(scanner.nextLine());
                if(date.isAfter(LocalDate.now())) {
                    System.out.println("Date of Birth cannot be in the future");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }
}
