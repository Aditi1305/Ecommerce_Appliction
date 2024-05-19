package com.example.productService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Category extends BaseModel{

    @OneToMany(fetch= FetchType.LAZY,mappedBy="category") //being already  mapped by product class , So no need to map again
    private List<Product> products;
    private String name;

}