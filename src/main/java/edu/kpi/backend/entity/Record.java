package edu.kpi.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Record {
    private final UUID id;
    private final UUID userId;
    private final UUID categoryId;
    private final LocalDate createdAt = LocalDate.now();
    private final int amount;
}
