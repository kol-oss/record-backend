package edu.kpi.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Account {
    private UUID id;
    private int balance;
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
