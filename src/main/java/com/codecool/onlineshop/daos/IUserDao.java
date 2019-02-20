package com.codecool.onlineshop.daos;


import com.codecool.onlineshop.containers.Basket;
import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.models.User;

import java.util.Date;
import java.util.List;


public interface IUserDao {

    User getUser(String login, String password) throws DAOException;

    List<Order> getOrdersByUserName(String userName) throws DAOException;

    void addOrder(String userLogin, String status, Date created_at, Basket basket) throws DAOException;

    List<Order> getAllOrders() throws DAOException;

    void addCustomer(String login, String password) throws DAOException;

    void changeStatusToPaid(int orderId) throws DAOException;
}
