package com.singlesSociety.UiModels;

public class ProductImageModel{
    private int id;
    private String imageUrl;
    private String productId;

    ProductImageModel(int id, String imageUrl, String productId){
        this.id = id;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductId() {
        return productId;
    }
}