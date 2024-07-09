package org.blog.blogging.services;

import lombok.RequiredArgsConstructor;
import org.blog.blogging.config.AppConstants;
import org.blog.blogging.entities.Customer;
import org.blog.blogging.entities.Role;
import org.blog.blogging.exceptions.EmailAlreadyExistsException;
import org.blog.blogging.exceptions.CustomerNotFoundException;
import org.blog.blogging.payloads.CustomerDTO;
import org.blog.blogging.payloads.RoleDTO;
import org.blog.blogging.repositories.CustomerRepository;
import org.blog.blogging.repositories.RoleRepository;
import org.blog.blogging.utils.CustomerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.*;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Customer with email already exists");
        }
        Customer customer=customerMapper.CustomerDTOtoCustomer(customerDTO);
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
        customer.getRoles().add(role);
        Customer saved = customerRepository.save(customer);
        return modelMapper.map(saved,CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Integer customerID) {
        Customer customer=customerRepository.findById(customerID)
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with Id %s Not Present",customerID)));
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        customer.setAbout(customerDTO.getAbout());
        Customer updatedCustomer=customerRepository.save(customer);
        return customerMapper.CustomertoCustomerDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(Integer customerID) {
        Customer customer=customerRepository.findById(customerID)
                .orElseThrow(()->new CustomerNotFoundException(format("Customer with Id %s Not Present",customerID)));
        return customerMapper.CustomertoCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getListOfCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream().
                map(customer -> customerMapper.CustomertoCustomerDTO(customer))
                .collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public void deleteCustomer(Integer customerID) {
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(()->new CustomerNotFoundException(format("Customer with Id %s Not Present",customerID)));
        customerRepository.delete(customer);
    }
}
