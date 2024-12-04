package edu.kpi.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int balance;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate lastUpdatedAt;

    public void add(int amount) {
        this.lastUpdatedAt = LocalDate.now();

        this.balance += amount;
    }

    public void remove(int amount) {
        this.lastUpdatedAt = LocalDate.now();

        this.balance -= amount;
    }
}
