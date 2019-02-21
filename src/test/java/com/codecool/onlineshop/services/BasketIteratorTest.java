package com.codecool.onlineshop.services;

import com.codecool.onlineshop.containers.Category;
import com.codecool.onlineshop.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketIteratorTest {
    private Product product;
    private List<Product> listOfProducts;

    @BeforeEach
    void setup() {
        product = new Product(1, "mama", 1, 1, true, new Category("dirt"), 3);
        listOfProducts = new ArrayList<>();
    }

    @Test
    void next() {
        listOfProducts.add(product);
        BasketIterator basketIterator = new BasketIterator(listOfProducts);
        assertEquals(product, basketIterator.next());
        listOfProducts.remove(product);
    }

    @Test
    void nextThrowsExceptionIfThereIsNoNextElement () {
        BasketIterator basketIterator = new BasketIterator(listOfProducts);
        assertNull(basketIterator.next());
    }

    @Test
    void hasNext() {
        listOfProducts.add(product);
        BasketIterator basketIterator = new BasketIterator(listOfProducts);
        assertTrue(basketIterator.hasNext());
        listOfProducts.remove(product);
    }
}