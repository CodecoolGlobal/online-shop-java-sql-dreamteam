package com.codecool.onlineshop.containers;

import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.services.BasketIterator;

import java.util.*;

public class Basket {
    private int id;
    private List<Product> list = new ArrayList<>();;



    public Basket(){
        
    }
    public Basket(int id, List<Product> list){
        this.id = id;
        this.list = list;
    }
    public Iterator getIterator(){
        return new BasketIterator(this.list);
    }


    public void addProduct(Product product){
        list.add(product);
    }

    public void deleteProduct(Product product){
        list.remove(product);
    }




}
