package edu.kpi.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Category {
    private final UUID id;
    private String name;
}
