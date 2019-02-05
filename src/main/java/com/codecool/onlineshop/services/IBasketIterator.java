package com.codecool.onlineshop.services;

import com.codecool.onlineshop.models.Product;

import java.util.Iterator;

public interface IBasketIterator extends Iterator {
    Product next();
    boolean hasNext();
}
