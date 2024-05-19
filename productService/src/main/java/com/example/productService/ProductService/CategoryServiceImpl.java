package com.example.productService.ProductService;

import com.example.productService.models.Category;
import com.example.productService.models.Product;
import com.example.productService.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategory() {
        return categoryRepository.findAll();

    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> optionalCategory=categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            System.out.println("Category with id:"+id+"is empty");
        }
        Category inputCategory= optionalCategory.get();
        inputCategory.setId(category.getId());
        inputCategory.setName(category.getName());

        return categoryRepository.save(inputCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> optionalProduct=categoryRepository.findById(id);
        if(optionalProduct.isEmpty()){
            System.out.println("This product with id:"+id+"is empty");
        }
        Category deleteProduct = optionalProduct.get();

        categoryRepository.delete(deleteProduct);
    }
}
