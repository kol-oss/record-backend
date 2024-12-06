package edu.kpi.backend.controller;

import edu.kpi.backend.dto.CreateCategoryDTO;
import edu.kpi.backend.entity.Category;
import edu.kpi.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/categories/")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(
                this.categoryService.getAllCategories()
        );
    }

    @GetMapping("{category_id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("category_id") UUID categoryId) {
        Optional<Category> category = this.categoryService.getCategoryById(categoryId);

        return category
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        return ResponseEntity.ok(this.categoryService.createCategory(createCategoryDTO));
    }

    @DeleteMapping("{category_id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("category_id") UUID categoryId) {
        Optional<Category> deleted = this.categoryService.deleteCategoryById(categoryId);

        return deleted
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
