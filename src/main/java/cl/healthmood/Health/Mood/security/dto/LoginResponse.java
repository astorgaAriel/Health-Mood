package cl.healthmood.Health.Mood.security.dto;


public record LoginResponse
        (String token,
     String email,
     String firstName,
     String lastName
        )
{}