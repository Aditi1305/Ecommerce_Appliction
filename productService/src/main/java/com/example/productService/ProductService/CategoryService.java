package com.example.productService.ProductService;

import com.example.productService.models.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(Category category);
    public List<Category> getCategory();
    public Category updateCategory(Long id, Category category);
    public void deleteCategory(Long id);
}
