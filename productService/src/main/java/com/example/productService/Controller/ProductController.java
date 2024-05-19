package com.example.productService.Controller;

import com.example.productService.ProductService.ProductService;
import com.example.productService.models.Category;
import com.example.productService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;
    RestTemplate restTemplate;
    @Autowired
    public ProductController(@Qualifier("SelfProduct") ProductService productService,RestTemplate restTemplate){
        this.productService=productService;
        this.restTemplate=restTemplate;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> products =  productService.getAllProduct();
        if(!products.isEmpty()) {
            return new ResponseEntity<List<Product>>(
                    products,
                    HttpStatus.OK);
        }
        ResponseEntity<List<Product>> response=new ResponseEntity<>(
                productService.getAllProduct(), HttpStatus.FORBIDDEN
        );
        return response;

    }
    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id){

        return productService.getSingleProduct(id);
    }
    @PostMapping()
    public Product addNewProduct(@RequestBody Product product){
        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){

        try{
            Product updateProduct = productService.updateProduct(id,product);
            return new ResponseEntity<Product>(updateProduct, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            ResponseEntity<Product> response=new ResponseEntity<>(
                    productService.updateProduct(id,product),HttpStatus.FORBIDDEN);
            return response;
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id,@RequestBody Product product){
        try{
            Product updateProduct=productService.replaceProduct(id,product);
            return new ResponseEntity<Product>(updateProduct,HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            ResponseEntity<Product> response=new ResponseEntity<>(
                    productService.replaceProduct(id,product),HttpStatus.FORBIDDEN);
            return response;
        }


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
        catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();

        }

    }

}
