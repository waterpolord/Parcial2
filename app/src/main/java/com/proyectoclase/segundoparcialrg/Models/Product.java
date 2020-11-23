package com.proyectoclase.segundoparcialrg.Models;

public class Product {
    private String name;
    private Double price;
    private Category category;

    public Product(String name, Double price,Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
