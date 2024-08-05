package tienda.tienda.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tienda.dto.CustomerDto;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.Customer;
import tienda.tienda.services.customer.CustomerService;
import tienda.tienda.transformers.CustomerMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Operations related to customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;


    @Operation(summary = "Get all customers")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Customer found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Customer not found", content = @Content)
    })
    @GetMapping
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerService.getAll();
        return customers.stream().map(customerMapper::toDto).toList();
    }



    @Operation(summary = "Get customer by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Customer found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Customer not found with id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@Parameter(description = "Customer id", example = "1")@PathVariable Long id) {
        Optional<Customer> customer = customerService.getById(id);
        return customer.map( c -> ResponseEntity.ok(customerMapper.toDto(c))).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Create customer in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Customer created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = customerMapper.toEntity(customerDto);
        Customer customerSaved = customerService.add(customer );
        return ResponseEntity.ok(customerMapper.toDto(customerSaved));
    }



    @Operation(summary = "Update customer in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Customer updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto){
        Customer customerToUpdate = customerMapper.toEntity(customerDto);
        Customer customerUpdated = customerService.update(customerToUpdate,id);
        return customerUpdated != null ? ResponseEntity.ok(customerMapper.toDto(customerUpdated)) : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Delete customer in database by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Customer deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@Parameter(description = "Customer id", example = "1")@PathVariable Long id) {
        if(customerService.getById(id).isPresent()){
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

