package cl.healthmood.Health.Mood.security.controller;

import cl.healthmood.Health.Mood.dto.CustomerRequest;
import cl.healthmood.Health.Mood.dto.CustomerResponse;
import cl.healthmood.Health.Mood.security.dto.LoginRequest;
import cl.healthmood.Health.Mood.security.dto.LoginResponse;
import cl.healthmood.Health.Mood.model.Role;
import cl.healthmood.Health.Mood.security.service.CustomerDetailsServiceImpl;
import cl.healthmood.Health.Mood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import cl.healthmood.Health.Mood.security.jwt.JwtUtils;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Lazy
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsServiceImpl customerDetailsService;
    private final CustomerService customerService;
    private final JwtUtils jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("🔐 Intento de login para email: {}", loginRequest.email());
            
            // Verificar que el usuario existe
            var customerOpt = customerDetailsService.findByEmail(loginRequest.email());
            if (customerOpt.isEmpty()) {
                log.warn("❌ Usuario no encontrado: {}", loginRequest.email());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
            }
            
            var customer = customerOpt.get();
            log.info("✅ Usuario encontrado: {} con rol: {}", customer.getEmail(), customer.getRole());
            
            // Verificar contraseña
            boolean passwordMatches = passwordEncoder.matches(loginRequest.password(), customer.getPassword());
            log.info("🔑 Password matches: {}", passwordMatches);
            
            if (!passwordMatches) {
                log.warn("❌ Contraseña incorrecta para: {}", loginRequest.email());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
            }

            // Autenticar cliente
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.email(), 
                    loginRequest.password()
                )
            );
            
            log.info("🎯 Autenticación exitosa para: {}", loginRequest.email());

            // Generar token
            String token = jwtUtil.generateToken(
                customer.getEmail(),
                customer.getRole().name(),
                customer.getCustomerId()
            );
            
            log.info("🔗 Token generado para: {}", loginRequest.email());

            // Crear respuesta usando LoginResponse
            LoginResponse response = new LoginResponse(
                token,
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName()
            );

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            log.error("Error de autenticación para email: {}", loginRequest.email());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales inválidas");
        } catch (Exception e) {
            log.error("Error inesperado en login: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CustomerRequest customerRequest) {
        try {
            log.info("🔓 Intentando registrar usuario con email: {}", customerRequest.getEmail());
            
            // Verificar si el email ya existe
            if (customerService.existsByEmail(customerRequest.getEmail())) {
                log.warn("⚠️ Email ya registrado: {}", customerRequest.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El email ya está registrado");
            }

            // Por defecto, los nuevos registros son CUSTOMER
            if (customerRequest.getRol() == null) {
                customerRequest.setRol(Role.CUSTOMER);
            }
            log.info("� Rol asignado: {} para {}", customerRequest.getRol(), customerRequest.getEmail());

            // Crear customer (la encriptación se hace en el service)
            CustomerResponse savedCustomer = customerService.save(customerRequest);
            log.info("✅ Cliente registrado exitosamente: {} con ID: {}", savedCustomer.getEmail(), savedCustomer.getCustomerId());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cliente registrado exitosamente");
            response.put("customerId", savedCustomer.getCustomerId());
            response.put("email", savedCustomer.getEmail());
            response.put("role", "CUSTOMER");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            log.error("❌ Error en registro: ", e);
            return ResponseEntity.badRequest()
                .body("Error al registrar cliente: " + e.getMessage());
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                
                if (jwtUtil.validateToken(token, email)) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("valid", true);
                    response.put("email", email);
                    response.put("role", jwtUtil.extractRole(token));
                    response.put("customerId", jwtUtil.extractCustomerId(token));
                    
                    return ResponseEntity.ok(response);
                }
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token inválido");
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token inválido");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("🧪 Endpoint de prueba accedido");
        return ResponseEntity.ok("AuthController funcionando correctamente");
    }

    @PostMapping("/create-test-user")
    public ResponseEntity<?> createTestUser() {
        try {
            String email = "test@test.com";
            
            // Verificar si ya existe
            if (customerService.existsByEmail(email)) {
                return ResponseEntity.ok("Usuario de prueba ya existe");
            }
            
            CustomerRequest testUser = CustomerRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email(email)
                .password(passwordEncoder.encode("123456"))
                .rol(Role.CUSTOMER)
                .build();
            
            CustomerResponse saved = customerService.save(testUser);
            log.info("✅ Usuario de prueba creado: {}", saved.getEmail());
            
            return ResponseEntity.ok("Usuario de prueba creado: " + email + " / password: 123456");
            
        } catch (Exception e) {
            log.error("❌ Error creando usuario de prueba: ", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
