package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.CustomerRequest;
import cl.healthmood.Health.Mood.dto.CustomerResponse;
import cl.healthmood.Health.Mood.mapper.CustomerMapper;
import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.toResponseList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerResponse> findById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponse);
    }

    @Override
    public CustomerResponse save(CustomerRequest customerRequest) {
        // Validaciones básicas (aunque también se validan con @Valid en el controller)
        if (customerRequest.getFirstName() == null || customerRequest.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (customerRequest.getLastName() == null || customerRequest.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (customerRequest.getEmail() == null || customerRequest.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // Verificar email único
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + customerRequest.getEmail());
        }

        // 🔒 Encriptar la contraseña si no está ya encriptada
        if (customerRequest.getPassword() != null && !customerRequest.getPassword().startsWith("$2a$")) {
            String encodedPassword = passwordEncoder.encode(customerRequest.getPassword());
            customerRequest.setPassword(encodedPassword);
        }

        // Crear entidad a partir del request
        Customer customer = customerMapper.toEntity(customerRequest);

        // Limpiar espacios en blanco
        customer.setFirstName(customer.getFirstName().trim());
        customer.setLastName(customer.getLastName().trim());
        customer.setEmail(customer.getEmail().trim());

        if (customer.getPhone() != null) {
            customer.setPhone(customer.getPhone().trim());
        }
        if (customer.getStreet() != null) {
            customer.setStreet(customer.getStreet().trim());
        }
        if (customer.getCity() != null) {
            customer.setCity(customer.getCity().trim());
        }
        if (customer.getCommune() != null) {
            customer.setCommune(customer.getCommune().trim());
        }
        // Note: role is an enum, no need to trim

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse update(Integer id, CustomerRequest customerRequest) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // Verificar email único si se está cambiando
                    if (customerRequest.getEmail() != null &&
                            !customerRequest.getEmail().equals(existingCustomer.getEmail()) &&
                            customerRepository.existsByEmail(customerRequest.getEmail())) {
                        throw new IllegalArgumentException("Email already exists: " + customerRequest.getEmail());
                    }

                    customerMapper.updateEntityFromRequest(customerRequest, existingCustomer);

                    // Limpiar espacios en blanco
                    if (existingCustomer.getFirstName() != null) {
                        existingCustomer.setFirstName(existingCustomer.getFirstName().trim());
                    }
                    if (existingCustomer.getLastName() != null) {
                        existingCustomer.setLastName(existingCustomer.getLastName().trim());
                    }
                    if (existingCustomer.getEmail() != null) {
                        existingCustomer.setEmail(existingCustomer.getEmail().trim());
                    }
                    if (existingCustomer.getPhone() != null) {
                        existingCustomer.setPhone(existingCustomer.getPhone().trim());
                    }
                    if (existingCustomer.getStreet() != null) {
                        existingCustomer.setStreet(existingCustomer.getStreet().trim());
                    }
                    if (existingCustomer.getCity() != null) {
                        existingCustomer.setCity(existingCustomer.getCity().trim());
                    }
                    if (existingCustomer.getCommune() != null) {
                        existingCustomer.setCommune(existingCustomer.getCommune().trim());
                    }
                    // Note: role is an enum, no need to trim

                    Customer updatedCustomer = customerRepository.save(existingCustomer);
                    return customerMapper.toResponse(updatedCustomer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerResponse> findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findByFirstName(String firstName) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(firstName);
        return customerMapper.toResponseList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findByLastName(String lastName) {
        List<Customer> customers = customerRepository.findByLastNameContainingIgnoreCase(lastName);
        return customerMapper.toResponseList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findByCity(String city) {
        List<Customer> customers = customerRepository.findByCity(city);
        return customerMapper.toResponseList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findByCommune(String commune) {
        List<Customer> customers = customerRepository.findByCommune(commune);
        return customerMapper.toResponseList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findByFullName(String name) {
        List<Customer> customers = customerRepository.findByFullNameContaining(name);
        return customerMapper.toResponseList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return customerRepository.existsById(id);
    }
}