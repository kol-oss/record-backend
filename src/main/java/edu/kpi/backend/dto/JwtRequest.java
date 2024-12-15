package edu.kpi.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {
    @NotNull(message = "Auth username can not be null")
    @NotEmpty(message = "Auth username can not be empty")
    private String username;

    @NotNull(message = "Auth password can not be null")
    @NotEmpty(message = "Auth password can not be empty")
    private String password;
}
