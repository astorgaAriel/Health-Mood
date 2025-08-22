package cl.healthmood.Health.Mood.security.service;

import cl.healthmood.Health.Mood.model.Customer;

import java.util.Optional;

public interface CustomerDetailsService {
    Optional<Customer> findByEmail(String email);
}