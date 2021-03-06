package com.codecool.onlineshop.models;

import com.codecool.onlineshop.containers.Category;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;
    private boolean isAvailable;
    private Category category;
    private float rate;


    public Product(int id, String name, double price, int amount, boolean isAvailable, Category category, float rate) {

        this.id = id;
        this.name = name;
        this.price = price; // rounds up to 2 digits past comma
        this.amount = amount;
        this.isAvailable = isAvailable;
        this.category = category;
        this.rate = rate;
    }

    public Product(String name, double price, int amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String productToString(){
        StringBuilder string = new StringBuilder();
        string.append("id: ");
        string.append(id);
        string.append(" name: ");
        string.append(name);
        string.append(" price: ");
        string.append(String.format("%.2f", price));
        string.append(" amount: ");
        string.append(amount);
        string.append(" isAvailable: ");
        string.append(isAvailable);
        string.append(" category: ");
        string.append(category.getName());
        string.append(" rate: ");
        string.append(getRate());

        return string.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getRate(){ return rate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
