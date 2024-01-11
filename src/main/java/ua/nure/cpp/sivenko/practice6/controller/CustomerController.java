package ua.nure.cpp.sivenko.practice6.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.cpp.sivenko.practice6.service.CustomerService;
import ua.nure.cpp.sivenko.practice6.model.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
@Log
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/customers/add")
    public String addCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "add_customer";
    }

    @PostMapping("/customers")
    public String addCustomer(@ModelAttribute("customer") Customer customer) {
        try {
            customerService.addCustomer(customer);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{customerId}")
    public String updateCustomerForm(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "update_customer";
    }

    @PostMapping("/customers/{customerId}")
    public String updateCustomer(@PathVariable Long customerId, @ModelAttribute("customer") Customer customer, Model model) {
        try {
            Customer customerById = customerService.getCustomerById(customerId);
            if (!Objects.equals(customerById, customer)) {
                customerService.updateCustomer(customerId, customer);
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId) {
        try {
            customerService.deleteCustomer(customerId);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return "redirect:/customers";
    }
}
