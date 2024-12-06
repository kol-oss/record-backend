package edu.kpi.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserDTO {
    @NotNull(message = "User name can not be null")
    @NotEmpty(message = "User name can not be empty")
    @Size(max = 64, message = "User name must be up to 64 symbols")
    private String name;
}
