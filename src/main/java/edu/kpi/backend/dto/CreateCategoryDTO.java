package edu.kpi.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCategoryDTO {
    @NotNull(message = "Category name can not be null")
    @NotEmpty(message = "Category name can not be empty")
    @Size(min = 4, max = 64, message = "Category name must be from 4 to 64 symbols")
    private String name;
}
