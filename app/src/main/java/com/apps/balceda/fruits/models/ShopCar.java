package com.apps.balceda.fruits.models;

import java.util.ArrayList;

public class ShopCar {
    private ArrayList<Fruit> order;
    private Product product;
    private double subTotal;
    private final double IGV = 0.18;
    private double igvAmount;
    private double total;

    public ShopCar() {
        order = new ArrayList<>();
        subTotal = 0.0;
        igvAmount = 0.0;
        total = 0.0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<Fruit> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Fruit> order) {
        this.order = order;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
        this.igvAmount = subTotal * IGV;
        this.total = this.subTotal + this.igvAmount;
    }

    public double getIgvAmount() {
        return igvAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
