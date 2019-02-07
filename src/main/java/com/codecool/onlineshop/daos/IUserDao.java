package com.codecool.onlineshop.daos;


import com.codecool.onlineshop.models.*;
import java.util.List;
import com.codecool.onlineshop.containers.*;
import java.util.Date;


public interface IUserDao {

    User getUser(String login, String password) throws DAOException;

    List<Order> getOrdersByUserName(String userName) throws DAOException;

    void addOrder(String userLogin, String status, Date created_at) throws DAOException;

    List<Order> getAllOrders() throws DAOException;

    void addCustomer(String login, String password) throws DAOException;
}
