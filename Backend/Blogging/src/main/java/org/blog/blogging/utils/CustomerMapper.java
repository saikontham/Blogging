package org.blog.blogging.utils;

import lombok.*;
import org.blog.blogging.entities.Customer;
import org.blog.blogging.payloads.CustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public Customer CustomerDTOtoCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO,Customer.class);
    }

    public CustomerDTO CustomertoCustomerDTO(Customer customer) {
        return modelMapper.map(customer,CustomerDTO.class);
    }
}
