package ua.nure.cpp.sivenko.practice6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.CustomerDAO;
import ua.nure.cpp.sivenko.practice6.model.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(long customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    public void addCustomer(Customer customer) throws SQLException {
        Customer customerByContactNumber = customerDAO.getCustomerByContactNumber(customer.getContactNumber());
        Customer customerByEmail = customerDAO.getCustomerByEmail(customer.getEmail());

        if (customerByContactNumber != null) {
            throw new SQLException("Customer with contact number '" + customer.getContactNumber() + "' already exists");
        } else if (customerByEmail != null) {
            throw new SQLException("Customer with email '" + customer.getEmail() + "' already exists");
        }
        customerDAO.addCustomer(customer);
    }

    public void deleteCustomer(long customerId) throws SQLException {
        Customer customerById = customerDAO.getCustomerById(customerId);
        if (customerById == null) {
            throw new SQLException("Customer with id '" + customerId + "' does not exists");
        }
        customerDAO.deleteCustomer(customerId);
    }

    public void updateCustomer(long customerId, Customer customer) throws SQLException {
        Customer customerById = customerDAO.getCustomerById(customerId);

        if (!Objects.equals(customerById.getContactNumber(), customer.getContactNumber())) {
            Customer customerByContactNumber = customerDAO.getCustomerByContactNumber(customer.getContactNumber());
            if (customerByContactNumber != null) {
                throw new SQLException("Customer with contact number '" + customer.getContactNumber() + "' already exists");
            }
        } else if (!Objects.equals(customerById.getEmail(), customer.getEmail())) {
            Customer customerByEmail = customerDAO.getCustomerByEmail(customer.getEmail());
            if (customerByEmail != null) {
                throw new SQLException("Customer with email '" + customer.getEmail() + "' already exists");
            }
        }
        customerDAO.updateCustomer(customerId, customer);
    }
}
