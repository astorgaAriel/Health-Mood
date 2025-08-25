package cl.healthmood.Health.Mood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Verificar conexión a la base de datos
            try (Connection connection = dataSource.getConnection()) {
                boolean isValid = connection.isValid(5);
                response.put("database", isValid ? "UP" : "DOWN");
            }

            response.put("status", "UP");
            response.put("application", "Health Mood API");
            response.put("version", "1.0.0");
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("database", "DOWN");
            response.put("error", e.getMessage());
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();

        response.put("application", "Health Mood E-commerce API");
        response.put("description", "API REST para tienda de mascotas Health Mood");
        response.put("version", "1.0.0");
        response.put("backend", "Spring Boot 3.5.4");
        response.put("database", "PostgreSQL");
        response.put("deployment", "Render Cloud Platform");
        response.put("frontend", "Vercel");

        return ResponseEntity.ok(response);
    }
}
