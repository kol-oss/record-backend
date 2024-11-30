package edu.kpi.backend.repository;

import edu.kpi.backend.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CategoryRepository {
    private final Set<Category> categories;

    public CategoryRepository() {
        this.categories = new HashSet<>();

        this.categories.add(new Category(UUID.randomUUID(), "Sport"));
    }

    public List<Category> getAll() {
        return this.categories.stream().toList();
    }

    public Optional<Category> getById(UUID id) {
        return this.categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    public Category create(String name) {
        Optional<Category> stored = this.categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst();

        if (stored.isPresent()) return stored.get();

        Category category = new Category(UUID.randomUUID(), name);
        this.categories.add(category);

        return category;
    }

    public Optional<Category> deleteById(UUID id) {
        Optional<Category> deleted = this.categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();

        deleted.ifPresent(this.categories::remove);

        return deleted;
    }
}
