package com.codecool.onlineshop.models;

import com.codecool.onlineshop.containers.Category;

import java.text.DecimalFormat;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;
    private boolean isAvailable;
    private Category category;

    public Product(int id, String name, double price, int amount, boolean isAvailable, Category category) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##");

        this.id = id;
        this.name = name;
        this.price = Double.valueOf(decimalFormat.format(Double.toString(price))); // rounds up to 2 digits past comma
        this.amount = amount;
        this.isAvailable = isAvailable;
        this.category = category;
    }

    public String prodctToString(){
        StringBuilder string = null;
        string.append("id: ");
        string.append(id);
        string.append(" name: ");
        string.append(name);
        string.append(" price: ");
        string.append(price);
        string.append(" amount: ");
        string.append(amount);
        string.append(" isAvailable: ");
        string.append(isAvailable);
        string.append(" category: ");
        string.append(category);

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

    public void setPrice(int price) {
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

}
