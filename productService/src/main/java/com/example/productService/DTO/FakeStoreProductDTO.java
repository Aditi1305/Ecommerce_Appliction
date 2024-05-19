package com.example.productService.DTO;

import com.example.productService.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
}
