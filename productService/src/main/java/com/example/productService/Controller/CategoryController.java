package com.example.productService.Controller;

import com.example.productService.ProductService.CategoryService;
import com.example.productService.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category category1 = categoryService.createCategory(category);
        if(category1!=null){
            return new ResponseEntity<>(category1, HttpStatus.OK);
        }
        ResponseEntity<Category> response=new ResponseEntity<>(
                categoryService.createCategory(category),HttpStatus.FORBIDDEN
        );
        return response;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategory(){
        List<Category> category=categoryService.getCategory();
        if(!category.isEmpty()){
            return new ResponseEntity<>(category,HttpStatus.OK);
        }
        ResponseEntity<List<Category>> response=new ResponseEntity<>(
                categoryService.getCategory(),HttpStatus.FORBIDDEN
        );
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        Category updatedCategory= categoryService.updateCategory(id,category);
        if(updatedCategory!=null){
            return new ResponseEntity<Category>(updatedCategory,HttpStatus.OK);
        }
        ResponseEntity<Category> response=new ResponseEntity<>(
                categoryService.updateCategory(id,category),HttpStatus.FORBIDDEN
        );
        return response;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
