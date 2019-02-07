package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;

public class Service {
    protected ProductDao productDao;
    protected UserDao userDao;

    protected Service() {
        productDao = new ProductDao();
        userDao = new UserDao();
    }
}
