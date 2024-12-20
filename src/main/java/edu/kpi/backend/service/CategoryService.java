package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateCategoryDTO;
import edu.kpi.backend.entity.Category;
import edu.kpi.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(UUID id) {
        return this.categoryRepository.findById(id);
    }

    public Category createCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = new Category(createCategoryDTO.getName());

        return this.categoryRepository.save(category);
    }

    public Optional<Category> deleteCategoryById(UUID id) {
        Optional<Category> category = this.categoryRepository.findById(id);

        this.categoryRepository.deleteById(id);

        return category;
    }
}
