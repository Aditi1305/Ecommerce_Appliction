package com.example.productService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String title;
    private String description;
    private String image;
    @ManyToOne(fetch= FetchType.EAGER,cascade={CascadeType.PERSIST})//(cascade={CascadeType.ALL}) //product : category
    private Category category;
    private double price;
}