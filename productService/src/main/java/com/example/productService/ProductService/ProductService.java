package com.example.productService.ProductService;

import com.example.productService.models.Category;
import com.example.productService.models.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    //public GenericProductDto getProductById(Long id);

    public void deleteProduct(Long id);
    public Product getSingleProduct(Long id);
    public List<Product> getAllProduct();
    public Product replaceProduct(Long id,Product product);
    public Product addNewProduct(Product product);
     public Product updateProduct(Long id, Product product);

}
