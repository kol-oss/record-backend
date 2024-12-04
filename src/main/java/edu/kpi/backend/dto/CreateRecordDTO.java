package edu.kpi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateRecordDTO {
    private UUID userId;
    private UUID categoryId;
    private int amount;
}
