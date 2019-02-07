package com.codecool.onlineshop.containers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void categoryToString() {
        Category category = new Category(1, "mama", true);
        assertEquals("id: 1 name: mama isAvailable: true\n Products in category: ", category.categoryToString());
    }
}