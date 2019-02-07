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
    private String status;

    public Order(int id, Basket basket, User user, Date orderCreatedAt, Date orderPayAt, String status){
        this.id = id;
        this.basket = basket;
        this.user = user;
        this.orderCreatedAt = orderCreatedAt;
        this.orderPayAt = orderPayAt;
        this.status = status;
    }

    private void pay(){
        paid = true;
    }

    private void setOrderPayAt(Date orderPayAt){
        this.orderPayAt = orderPayAt;
    }


}
