package com.codecool.onlineshop.containers;

import com.codecool.onlineshop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private boolean isAvailable;
    private List<Product> list = new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

    public Category(int id, String name, boolean isAvailable, List<Product> products) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
        this.list = products;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public List<Product> getList() {
        return list;
    }


}
