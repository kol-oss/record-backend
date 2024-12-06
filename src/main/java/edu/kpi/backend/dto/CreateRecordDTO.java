package edu.kpi.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateRecordDTO {
    @NotNull(message = "User id in record can not be null")
    private UUID userId;

    @NotNull(message = "Category id in record can not be null")
    private UUID categoryId;

    @NotNull(message = "Record sum can not be null")
    private int amount;
}
