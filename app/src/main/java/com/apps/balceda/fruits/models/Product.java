package com.apps.balceda.fruits.models;

public class Product {
  private String name;
  private String image;
  private String price;

  public Product() {
  }

  public Product(String name, String image, String price) {
    this.name = name;
    this.image = image;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
