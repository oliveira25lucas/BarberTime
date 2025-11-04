package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.CustomerDTO;
import com.oliveiralucas.barber_time.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer/v1")
@Tag(name = "Customer", description = "Endpoints for Customer")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Customer",
            tags = {"Customer"})
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customer) {
        return customerService.create(customer);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all Customer",
            tags = {"Customer"})
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a Customer",
            tags = {"Customer"})
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Customer",
            tags = {"Customer"})
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        return customerService.update(id, customer);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Customer",
            tags = {"Customer"})
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }

}
