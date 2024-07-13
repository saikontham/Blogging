package org.blog.blogging.config;

import org.blog.blogging.entities.Customer;
import org.blog.blogging.exceptions.CustomerNotFoundException;
import org.blog.blogging.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service

public class CustomCustomerDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new CustomerNotFoundException(format("Customer with email %s not found", username)));
        return customer;
    }
}
