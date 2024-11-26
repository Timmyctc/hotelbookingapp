package org.dip.tus.customer;

import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerManager {

    private static final CustomerManager instance = new CustomerManager();
    private final Map<String, Customer> customerRegistry = new HashMap<>();

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        return instance;
    }

    private String generateKey(String name, LocalDate dob) {
        return name.toLowerCase() + "|" + dob.toString();
    }

    public Customer addCustomer(Customer customer) {
        String key = generateKey(customer.getName(), customer.getDateOfBirth());
        return customerRegistry.computeIfAbsent(key, k -> customer);
    }

    public Customer getCustomer(String name, LocalDate dob) {
        String key = generateKey(name, dob);
        return customerRegistry.get(key);
    }

    /**
     * Retrieves all customers with the given name (case-insensitive).
     *
     * @param name Customer name to search for.
     * @return A list of matching customers.
     */
    public List<Customer> getCustomersByName(String name) {
        return customerRegistry.values().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an existing customer with the given name or adds a new customer if none exists.
     *
     * @param name Customer name to search for.
     * @param dob  Customer date of birth.
     * @return The existing or newly added customer.
     */
    public Customer getCustomerOrAdd(String name, LocalDate dob) {
        Customer existingCustomer = getCustomer(name, dob);
        if (existingCustomer != null) {
            return existingCustomer;
        }
        Customer newCustomer = new Customer(name, dob);
        return addCustomer(newCustomer);
    }

    public List<Customer> getCustomerList() {
        return customerRegistry.values().stream().collect(Collectors.toList());
    }

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
            System.out.printf(ConsoleColour.CYAN + "| %-21s | %-18s |\n" + ConsoleColour.RESET,
                    customer.getName(),
                    customer.getDateOfBirth().toString());
        }

        System.out.println(ConsoleColour.BLUE + "+--------------------------------------------+" + ConsoleColour.RESET);
    }
}
