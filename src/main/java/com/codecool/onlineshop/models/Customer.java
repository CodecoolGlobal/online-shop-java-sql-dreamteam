package com.codecool.onlineshop.models;

import com.codecool.onlineshop.containers.Basket;
import com.codecool.onlineshop.containers.Order;

public class Customer extends User {
    private Basket basket;
    private Order order;

    public Customer(String name, String password) {
        super(name, password);
        basket = new Basket();
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }



}
