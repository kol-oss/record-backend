package edu.kpi.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
