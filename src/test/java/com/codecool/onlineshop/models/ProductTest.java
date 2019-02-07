package com.codecool.onlineshop.models;

import com.codecool.onlineshop.containers.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    public void productToString() {
        Product product = new Product(1, "mama", 1 , 1, true, new Category("dirt"));
        assertEquals("id: 1 name: mama price: 1,00 amount: 1 isAvailable: true category: dirt", product.productToString());
    }

}