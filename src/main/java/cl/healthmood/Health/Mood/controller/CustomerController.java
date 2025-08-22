package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.CustomerRequest;
import cl.healthmood.Health.Mood.dto.CustomerResponse;
import cl.healthmood.Health.Mood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer id) {
        // Verificar si el usuario es ADMIN o si está accediendo a su propia información
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        
        // Si no es admin, verificar que esté accediendo a su propia información
        if (!isAdmin) {
            CustomerResponse currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
            if (currentCustomer == null || !currentCustomer.getCustomerId().equals(id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        try {
            // Verificar si se está intentando crear un ADMIN
            if (customerRequest.getRol() != null && customerRequest.getRol().name().equals("ADMIN")) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                boolean isAdmin = auth.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
                
                if (!isAdmin) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Solo los administradores pueden crear otros administradores");
                }
            }
            
            CustomerResponse savedCustomer = customerService.save(customerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already exists: " + customerRequest.getEmail());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating customer");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerRequest customerRequest) {
        try {
            // Verificar permisos: ADMIN puede editar cualquiera, CUSTOMER solo a sí mismo
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = auth.getName();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            
            if (!isAdmin) {
                CustomerResponse currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
                if (currentCustomer == null || !currentCustomer.getCustomerId().equals(id)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("No tienes permisos para editar este customer");
                }
                
                // Un customer no puede cambiar su propio rol a ADMIN
                if (customerRequest.getRol() != null && customerRequest.getRol().name().equals("ADMIN")) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("No puedes cambiar tu rol a ADMIN");
                }
            } else {
                // Si es admin y está tratando de crear otro admin, está permitido
                // Verificación adicional: si se está cambiando a rol ADMIN
                if (customerRequest.getRol() != null && customerRequest.getRol().name().equals("ADMIN")) {
                    // Solo admin puede hacer esto (ya verificado arriba)
                }
            }
            
            CustomerResponse updatedCustomer = customerService.update(id, customerRequest);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Customer not found")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already exists: " + customerRequest.getEmail());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating customer");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        try {
            customerService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Customer not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse> getCurrentCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        
        return customerService.findByEmail(currentUserEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@PathVariable String email) {
        return customerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/firstname/{firstName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getCustomersByFirstName(@PathVariable String firstName) {
        List<CustomerResponse> customers = customerService.findByFirstName(firstName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/lastname/{lastName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getCustomersByLastName(@PathVariable String lastName) {
        List<CustomerResponse> customers = customerService.findByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/city/{city}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getCustomersByCity(@PathVariable String city) {
        List<CustomerResponse> customers = customerService.findByCity(city);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/commune/{commune}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getCustomersByCommune(@PathVariable String commune) {
        List<CustomerResponse> customers = customerService.findByCommune(commune);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/fullname/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getCustomersByFullName(@PathVariable String name) {
        List<CustomerResponse> customers = customerService.findByFullName(name);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/exists/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = customerService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkCustomerExists(@PathVariable Integer id) {
        boolean exists = customerService.existsById(id);
        return ResponseEntity.ok(exists);
    }
}