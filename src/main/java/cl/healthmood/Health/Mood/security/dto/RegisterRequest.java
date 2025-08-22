package cl.healthmood.Health.Mood.security.dto;

public record RegisterRequest(
        String firs,
        String email,
        String password
) {}