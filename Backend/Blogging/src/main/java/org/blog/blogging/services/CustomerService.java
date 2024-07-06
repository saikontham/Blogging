package org.blog.blogging.services;

import org.blog.blogging.payloads.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customer);
    CustomerDTO updateCustomer(CustomerDTO customer,Integer customerID);
    CustomerDTO getCustomerById(Integer customerID);
    List<CustomerDTO> getListOfCustomers();
    void deleteCustomer(Integer customerID);
}
