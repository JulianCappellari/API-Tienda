package tienda.tienda.services.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tienda.entities.Customer;
import tienda.tienda.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    public  CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer add(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer update(Customer entity, Long id) {
        return customerRepository.findById(id).map( customer -> {
            customer.setName(entity.getName());
            customer.setEmail(entity.getEmail());
            customer.setAddress(entity.getAddress());
            customer.setNumber(entity.getNumber());
            return customerRepository.save(customer);
        }).orElse(null);
    }
}
