package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.CustomerDAO;
import ua.nure.cpp.sivenko.practice6.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerDAOCollectionImpl implements CustomerDAO {
    private final List<Customer> customers = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public CustomerDAOCollectionImpl() {
        addCustomer(new Customer(12, "Daniel", "Runolfsson", "983-346-2564", "Daniel.Runolfsson@gmail.com"));
        addCustomer(new Customer(5, "Sheila", "Ernser", "165-155-8744", "Sheila.Ernser@gmail.com"));
        addCustomer(new Customer(23, "Terence", "Wehner", "586-868-0447", "Terence.Wehner42@yahoo.com"));
    }

    @Override
    public Customer getCustomerById(long customerId) {
        return customers.stream()
                .filter(customer -> customer.getCustomerId() == customerId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Customer getCustomerByContactNumber(String contactNumber) {
        return customers.stream()
                .filter(customer -> customer.getContactNumber().equals(contactNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        customer.setCustomerId(id.getAndIncrement());
        customers.add(customer);
    }

    @Override
    public void updateCustomer(long customerId, Customer customer) {
        customers.set((int) customerId - 1, customer);
    }

    @Override
    public void deleteCustomer(long customerId) {
        customers.remove((int) customerId - 1);
    }
}
