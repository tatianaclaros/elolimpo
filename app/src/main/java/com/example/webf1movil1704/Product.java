package com.example.webf1movil1704;

public class Product {
    private final String name;
    private final int imageResId;
    private final String price;


    public Product(String name, int imageResId, String price) {
        this.name = name;
        this.imageResId = imageResId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPrice() {
        return price;
    }
}
