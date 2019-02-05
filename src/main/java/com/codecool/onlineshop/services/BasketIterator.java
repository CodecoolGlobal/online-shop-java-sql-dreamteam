package com.codecool.onlineshop.services;

import com.codecool.onlineshop.models.Product;

import java.util.List;

public class BasketIterator implements IBasketIterator {
    private int index;
    private List<Product> list;

    public BasketIterator(List<Product> list){
        this.list = list;
    }

    @Override
    public Product next() {
        if(this.hasNext()){
            return list.get(index++);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        if(index < list.size()){
            return true;
        }

        return false;
    }
}
