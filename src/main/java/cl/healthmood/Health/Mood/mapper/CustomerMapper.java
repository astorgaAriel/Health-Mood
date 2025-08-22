package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.CustomerRequest;
import cl.healthmood.Health.Mood.dto.CustomerResponse;
import cl.healthmood.Health.Mood.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            return null;
        }

        return Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .password(customerRequest.getPassword())
                .role(customerRequest.getRol()) // 👤 Mapear el rol
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .street(customer.getStreet())
                .city(customer.getCity())
                .commune(customer.getCommune())
                .registerDate(customer.getRegisterDate())
                .rol(customer.getRole() != null ? customer.getRole().name() : null)
                .build();
    }

    public List<CustomerResponse> toResponseList(List<Customer> customers) {
        if (customers == null) {
            return null;
        }

        return customers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(CustomerRequest customerRequest, Customer customer) {
        if (customerRequest == null || customer == null) {
            return;
        }

        if (customerRequest.getFirstName() != null) {
            customer.setFirstName(customerRequest.getFirstName());
        }
        if (customerRequest.getLastName() != null) {
            customer.setLastName(customerRequest.getLastName());
        }

        if (customerRequest.getEmail() != null) {
            customer.setEmail(customerRequest.getEmail());
        }

        if (customerRequest.getPassword() != null) {
            customer.setPassword(customerRequest.getPassword());
        }

        if (customerRequest.getRol() != null) {
            customer.setRole(customerRequest.getRol());
        }

    }
}