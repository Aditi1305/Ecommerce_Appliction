package com.example.productService.ProductService;

import com.example.productService.DTO.FakeStoreProductDTO;
import com.example.productService.models.Product;
import com.example.productService.models.Category;
import com.example.productService.repositories.CategoryRepository;
import com.example.productService.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("SelfProduct")
@Transactional
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public SelfProductService(ProductRepository productRepository,CategoryRepository categoryRepository){
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
    }



    @Override
    public Product getSingleProduct(Long id) throws NoSuchElementException {
        Optional<Product> optionalProduct=productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            System.out.println("This product with id:"+id+"is empty");
        }
        Product product=optionalProduct.get();

        return product;
    }

    @Override
    public List<Product> getAllProduct() {

        return productRepository.findAll();
    }


    public Product replaceProduct(Long id, Product product) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            System.out.println("This product with id:"+id+"is empty");
        }
        Product updateProduct=optionalProduct.get();
        updateProduct.setId(product.getId());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setTitle(product.getTitle());
        updateProduct.setImage(product.getImage());
        updateProduct.setCategory(product.getCategory());
        return productRepository.save(updateProduct);

    }
    public Product addNewProduct(Product product)
    {
        Optional<Category> optionalCategory=categoryRepository.findByName(product.getCategory());
        if(optionalCategory.isEmpty()){
            //product.setCategory(categoryRepository.save(product.getCategory()));
        }
        else{
            product.setCategory(optionalCategory.get());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            System.out.println("This product with id:"+id+"is empty");
        }
        Product inputProduct= optionalProduct.get();
        if(product.getDescription()!=null){
            inputProduct.setDescription(product.getDescription());
        }
        if(product.getCategory()!=null){
            inputProduct.setCategory(product.getCategory());
        }
        if(product.getPrice()!= 0.0){
            inputProduct.setPrice(product.getPrice());
        }
        if(product.getImage()!=null){
            inputProduct.setImage(product.getImage());
        }
        return productRepository.save(inputProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            System.out.println("This product with id:"+id+"is empty");
        }
        Product deleteProduct = optionalProduct.get();

        productRepository.delete(deleteProduct);
    }
}
