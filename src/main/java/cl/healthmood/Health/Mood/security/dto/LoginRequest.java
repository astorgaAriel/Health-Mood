package cl.healthmood.Health.Mood.security.dto;

public record LoginRequest(
        String email,
        String password
) {}