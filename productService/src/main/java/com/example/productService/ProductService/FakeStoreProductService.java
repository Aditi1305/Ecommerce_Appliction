package com.example.productService.ProductService;

import com.example.productService.DTO.FakeStoreProductDTO;
import com.example.productService.models.Category;
import com.example.productService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("fakeStoreProduct")
public class FakeStoreProductService{
    private RestTemplate restTemplate;
    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDTO fakeStoreProductDTO){
        Product product=new Product();
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setImage(fakeStoreProductDTO.getImage());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDTO.getCategory());
        return product;
    }

    public Product getSingleProduct(Long id) {
        FakeStoreProductDTO productDTO=restTemplate.getForObject(
                "https://fakestoreapi.com/products/" +id,
                FakeStoreProductDTO.class
        );
        return convertFakeStoreProductToProduct(productDTO);
    }


    public List<Product> getAllProduct() {
        FakeStoreProductDTO[] productDto=restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class
        );
        List<Product> answer=new ArrayList<>();
        for(FakeStoreProductDTO response: productDto){
            answer.add(convertFakeStoreProductToProduct(response));
        }
        return answer;
    }


    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setImage(product.getImage());

        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDTO, FakeStoreProductDTO.class);
        HttpMessageConverterExtractor<FakeStoreProductDTO> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDTO.class, restTemplate.getMessageConverters());
        FakeStoreProductDTO reponse=restTemplate.execute("https://fakestoreapi.com/products/" +id, HttpMethod.PUT, requestCallback, responseExtractor);
        return convertFakeStoreProductToProduct(reponse);
    }


    public Product addNewProduct(Product product) {
        return null;
    }


    public Product updateProduct(Long id, Product product) {
        return null;
    }





    /*private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public GenericProductDto getProductById(Long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();

        System.out.println("this is fake store api");
        return null;
    }*/
}
