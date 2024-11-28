package org.dip.tus.customer;

import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class that manages the registration and retrieval of customers.
 */
public class CustomerManager {

    private static final CustomerManager instance = new CustomerManager();
    private final Map<String, Customer> customerRegistry = new HashMap<>();


    private CustomerManager() {
    }


    public static CustomerManager getInstance() {
        return instance;
    }

    /**
     * Generates a unique key for a customer based on their name and date of birth.
     *
     * @param name the customer's name.
     * @param dob  the customer's date of birth.
     * @return the generated unique key.
     */
    private String generateKey(String name, LocalDate dob) {
        return name.toLowerCase() + "|" + dob.toString();
    }

    /**
     * Adds a customer to the registry if they don't already exist.
     *
     * @param customer the customer to add.
     * @return the existing or newly added customer.
     */
    public Customer addCustomer(Customer customer) {
        String key = generateKey(customer.getName(), customer.getDateOfBirth());
        return customerRegistry.computeIfAbsent(key, k -> customer);
    }

    /**
     * Retrieves a customer based on their name and date of birth.
     *
     * @param name the customer's name.
     * @param dob  the customer's date of birth.
     * @return the customer if found, or {@code null} otherwise.
     */
    public Customer getCustomer(String name, LocalDate dob) {
        String key = generateKey(name, dob);
        return customerRegistry.get(key);
    }

    /**
     * Retrieves all customers with the given name (case-insensitive).
     *
     * @param name the name to search for.
     * @return a list of customers with the given name.
     */
    public List<Customer> getCustomersByName(String name) {
        return customerRegistry.values().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an existing customer or adds a new customer if none exists.
     *
     * @param name the customer's name.
     * @param dob  the customer's date of birth.
     * @return the existing or newly added customer.
     */
    public Customer getCustomerOrAdd(String name, LocalDate dob) {
        Customer existingCustomer = getCustomer(name, dob);
        if (existingCustomer != null) {
            return existingCustomer;
        }
        Customer newCustomer = new Customer(name, dob);
        return addCustomer(newCustomer);
    }

    /**
     * Retrieves a list of all registered customers.
     *
     * @return a list of all customers.
     */
    public List<Customer> getCustomerList() {
        return customerRegistry.values().stream().collect(Collectors.toList());
    }

    /**
     * Displays all registered customers in a formatted table.
     */
    public void displayAllCustomers() {
        List<Customer> customers = getCustomerList();

        if (customers.isEmpty()) {
            System.out.println(ConsoleColour.RED + "No customers are currently registered." + ConsoleColour.RESET);
            return;
        }

        System.out.println(ConsoleColour.BLUE + "+--------------------------------------------+");
        System.out.println("|              Registered Customers          |");
        System.out.println("+--------------------------------------------+");
        System.out.println("| Name                  | Date of Birth      |");
        System.out.println("+-----------------------+--------------------+" + ConsoleColour.RESET);

        for (Customer customer : customers) {
            System.out.printf(ConsoleColour.CYAN + "| %-21s | %-18s |\n" + ConsoleColour.RESET, customer.getName(),
                    customer.getDateOfBirth().toString());
        }
        System.out.println(ConsoleColour.BLUE + "+--------------------------------------------+" + ConsoleColour.RESET);
    }
}
