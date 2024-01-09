package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer getCustomerById(long customerId);

    Customer getCustomerByContactNumber(String contactNumber);

    Customer getCustomerByEmail(String email);

    List<Customer> getAllCustomers();

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(long customerId);
}
