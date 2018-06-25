package com.apps.balceda.fruits.models;

import java.util.ArrayList;

public class ShopCar {
    private ArrayList<Fruit> pedido;
    private Product product;
    private double subTotal;
    private final double IGV = 0.18;
    private double igvCompra;
    private double totalCompra;

    public ShopCar() {
        pedido = new ArrayList<>();
        subTotal = 0.0;
        igvCompra = 0.0;
        totalCompra = 0.0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<Fruit> getPedido() {
        return pedido;
    }

    public void setPedido(ArrayList<Fruit> pedido) {
        this.pedido = pedido;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
        this.igvCompra = subTotal * IGV;
        this.totalCompra = this.subTotal + this.igvCompra;
    }

    public double getIgvCompra() {
        return igvCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }
}
