package com.codecool.onlineshop.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void getName() {
        User user = new User("mama", "123");
        assertEquals("mama", user.getName());
        assertEquals("123", user.getPassword());
    }

}