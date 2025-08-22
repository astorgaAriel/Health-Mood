package cl.healthmood.Health.Mood.security.service;

import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsServiceImpl.class);

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Buscando cliente con email: {}", email);
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isEmpty()) {
            logger.warn("No se encontró cliente con email: {}", email);
            throw new UsernameNotFoundException("Cliente no encontrado con email: " + email);
        }
        Customer customer = customerOpt.get();

        if (customer.getRole() == null) {
            logger.error("El cliente con email {} no tiene rol asignado.", email);
            throw new UsernameNotFoundException("El cliente no tiene rol asignado: " + email);
        }
        String role = customer.getRole().name();
        logger.info("Rol obtenido para el cliente {}: {}", email, role);

        return new User(
                customer.getEmail(),
                customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
