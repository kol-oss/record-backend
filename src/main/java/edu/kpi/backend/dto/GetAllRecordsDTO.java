package edu.kpi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetAllRecordsDTO {
    private UUID userId;
    private UUID categoryId;
}
