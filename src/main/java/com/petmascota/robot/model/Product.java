package com.petmascota.robot.model;

/**
 * Product
 * @author agustinadagnino
 *
 */
public class Product {

    private String title ;
    private String description;
    private String url ;
    private String regularPriceDesc ;
    private String regularPrice ;
    private String promotionPriceDesc ;
    private String promotionPrice ;
    private String brand ;
    private String size ;
    
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getRegularPrice() {
        return regularPrice;
    }
    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }
    public String getPromotionPrice() {
        return promotionPrice;
    }
    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getRegularPriceDesc() {
        return regularPriceDesc;
    }
    public void setRegularPriceDesc(String regularPriceDesc) {
        this.regularPriceDesc = regularPriceDesc;
    }
    public String getPromotionPriceDesc() {
        return promotionPriceDesc;
    }
    public void setPromotionPriceDesc(String promotionPriceDesc) {
        this.promotionPriceDesc = promotionPriceDesc;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Object clone(){
        Product other = new Product();
        other.setBrand(this.getBrand());
        other.setTitle(this.getTitle());
        other.setUrl(this.getUrl());
        return other;
    }
    
    public String toString(){
        return "" + getTitle() + " [" + getBrand() + "] - "+getSize();
    }
    
}
