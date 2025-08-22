package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.CustomerRequest;
import cl.healthmood.Health.Mood.dto.CustomerResponse;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerResponse> findAll();

    Optional<CustomerResponse> findById(Integer id);

    CustomerResponse save(CustomerRequest customerRequest);

    CustomerResponse update(Integer id, CustomerRequest customerRequest);

    void deleteById(Integer id);

    Optional<CustomerResponse> findByEmail(String email);

    List<CustomerResponse> findByFirstName(String firstName);

    List<CustomerResponse> findByLastName(String lastName);

    List<CustomerResponse> findByCity(String city);

    List<CustomerResponse> findByCommune(String commune);

    List<CustomerResponse> findByFullName(String name);

    boolean existsByEmail(String email);

    boolean existsById(Integer id);
}