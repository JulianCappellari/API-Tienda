package tienda.tienda.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tienda.dto.CustomerDto;
import tienda.tienda.entities.Customer;
import tienda.tienda.services.customer.CustomerService;
import tienda.tienda.transformers.CustomerMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerService.getAll();
        return customers.stream().map(customerMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getById(id);
        return customer.map( c -> ResponseEntity.ok(customerMapper.toDto(c))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = customerMapper.toEntity(customerDto);
        Customer customerSaved = customerService.add(customer );
        return ResponseEntity.ok(customerMapper.toDto(customerSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto){
        Customer customerToUpdate = customerMapper.toEntity(customerDto);
        Customer customerUpdated = customerService.update(customerToUpdate,id);
        return customerUpdated != null ? ResponseEntity.ok(customerMapper.toDto(customerUpdated)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable Long id) {
        if(customerService.getById(id).isPresent()){
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

