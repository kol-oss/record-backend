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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 64)
    private String name;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public User(String name, Account account) {
        this.name = name;
        this.account = account;
    }
}
