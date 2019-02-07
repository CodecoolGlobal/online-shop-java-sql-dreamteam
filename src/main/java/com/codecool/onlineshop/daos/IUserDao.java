package com.codecool.onlineshop.daos;


import com.codecool.onlineshop.models.*;
import java.util.List;
import com.codecool.onlineshop.containers.*;
import java.util.Date;


public interface IUserDao {

    User getUser(String login, String password);

    List<Order> getOrdersByUserName(String userName);

    void addOrder(String userLogin, String status, Date created_at, Date paid_at);

    List<Order> getAllOrders();

    void addCustomer(String login, String password);
}
