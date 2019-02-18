package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.IProductDao;
import com.codecool.onlineshop.daos.IUserDao;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;

public class Service {
    protected ProductDao productDao; // why cant i just change it to interface type?
    protected UserDao userDao;

    protected Service() {
        productDao = new ProductDao();
        userDao = new UserDao();
    }
}
