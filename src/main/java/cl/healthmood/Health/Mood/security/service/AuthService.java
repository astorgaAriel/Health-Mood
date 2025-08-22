package cl.healthmood.Health.Mood.security.service;

import cl.healthmood.Health.Mood.dto.CustomerRequest;
import cl.healthmood.Health.Mood.dto.CustomerResponse;
import cl.healthmood.Health.Mood.security.dto.LoginRequest;
import cl.healthmood.Health.Mood.security.dto.LoginResponse;
import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.model.Role;
import cl.healthmood.Health.Mood.repository.CustomerRepository;
import cl.healthmood.Health.Mood.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), customer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(
                customer.getEmail(), 
                customer.getRole().name(), 
                customer.getCustomerId()
        );

        return new LoginResponse(
                token,
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName()
        );
    }

    public CustomerResponse register(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRol() != null ? request.getRol() : Role.CUSTOMER)
                .registerDate(LocalDate.now())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .customerId(savedCustomer.getCustomerId())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .phone(savedCustomer.getPhone())
                .email(savedCustomer.getEmail())
                .street(savedCustomer.getStreet())
                .city(savedCustomer.getCity())
                .commune(savedCustomer.getCommune())
                .registerDate(savedCustomer.getRegisterDate())
                .rol(savedCustomer.getRole() != null ? savedCustomer.getRole().name() : null)
                .build();
    }

    public LoginResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token format");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        
        if (jwtUtil.isTokenExpired(token)) {
            throw new RuntimeException("Token expired");
        }

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newToken = jwtUtil.generateToken(
                customer.getEmail(), 
                customer.getRole().name(), 
                customer.getCustomerId()
        );

        return new LoginResponse(
                newToken,
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName()
        );
    }
}