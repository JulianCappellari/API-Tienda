package tienda.tienda.transformers;

import org.springframework.stereotype.Component;
import tienda.tienda.dto.CustomerDto;
import tienda.tienda.entities.Customer;

@Component
public class CustomerMapper {

    public CustomerDto toDto(Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getNumber()
        );
    }

    public Customer toEntity( CustomerDto customerDto) {
        return new Customer(
                customerDto.getId() != null ? customerDto.getId().longValue() : null,
                customerDto.getName(),
                customerDto.getEmail(),
                customerDto.getAddress(),
                customerDto.getNumber(),
                null
        );
    }
}
