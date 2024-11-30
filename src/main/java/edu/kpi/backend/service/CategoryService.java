package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateCategoryDTO;
import edu.kpi.backend.entity.Category;
import edu.kpi.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return this.categoryRepository.getAll();
    }

    public Optional<Category> getCategoryById(UUID id) {
        return this.categoryRepository.getById(id);
    }

    public Category createCategory(CreateCategoryDTO createCategoryDTO) {
        return this.categoryRepository.create(createCategoryDTO.getName());
    }

    public Optional<Category> deleteCategoryById(UUID id) {
        return this.categoryRepository.deleteById(id);
    }
}
