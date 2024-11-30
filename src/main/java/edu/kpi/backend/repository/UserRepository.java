package edu.kpi.backend.repository;

import edu.kpi.backend.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Set<User> users;

    public UserRepository() {
        this.users = new HashSet<>();

        this.users.add(new User(UUID.randomUUID(), "Jack"));
    }

    public List<User> getAll() {
        return this.users.stream().toList();
    }

    public Optional<User> getById(UUID id) {
        return this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User create(String name) {
        Optional<User> stored = this.users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();

        if (stored.isPresent()) return stored.get();

        User user = new User(UUID.randomUUID(), name);
        this.users.add(user);

        return user;
    }

    public Optional<User> deleteById(UUID id) {
        Optional<User> deleted = this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        deleted.ifPresent(this.users::remove);

        return deleted;
    }
}
