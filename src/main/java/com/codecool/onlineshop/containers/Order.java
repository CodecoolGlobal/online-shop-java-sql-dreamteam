package com.codecool.onlineshop.containers;

import com.codecool.onlineshop.models.User;

import java.util.Date;

public class Order {
    private int id;
    private Basket basket;
    private User user;
    private Date orderCreatedAt;
    private boolean paid = false; // OrderStatus orderstatus?? - look: uml
    private Date orderPayAt;

    public Order(int id, Basket basket, User user, Date orderCreatedAt){
        this.id = id;
        this.basket = basket;
        this.user = user;
        this.orderCreatedAt = orderCreatedAt;
    }

    private void pay(){
        paid = true;
    }

    private void setOrderPayAt(Date orderPayAt){
        this.orderPayAt = orderPayAt;
    }
}
