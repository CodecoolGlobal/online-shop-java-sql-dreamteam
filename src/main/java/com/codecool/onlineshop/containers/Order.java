package com.codecool.onlineshop.containers;

import com.codecool.onlineshop.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order id: ");
        sb.append(id);
        sb.append(", status: ");
        sb.append(status);
        sb.append(", user: ");
        sb.append(user.getName());
        sb.append(", created at: ");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        sb.append(df.format(orderCreatedAt));
        if (orderPayAt != null) {
            sb.append(", paid At: ");
            sb.append(df.format(orderPayAt));
        }
        return sb.toString();
    }


}
