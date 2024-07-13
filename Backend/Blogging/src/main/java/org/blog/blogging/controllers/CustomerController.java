package org.blog.blogging.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.blog.blogging.payloads.ApiResponse;
import org.blog.blogging.payloads.CustomerDTO;
import org.blog.blogging.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid  @RequestBody CustomerDTO customer) {
        CustomerDTO createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{customerID}")
    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customer,
                                                      @PathVariable Integer customerID) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(customer, customerID);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @GetMapping("/{customerID}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer customerID) {
        CustomerDTO customer = customerService.getCustomerById(customerID);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getListOfCustomers() {
        List<CustomerDTO> customers = customerService.getListOfCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{customerID}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Integer customerID) {
        customerService.deleteCustomer(customerID);
        return ResponseEntity.ok(new ApiResponse("Deleted successfully",true));
    }
}
