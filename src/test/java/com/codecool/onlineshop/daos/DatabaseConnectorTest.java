package com.codecool.onlineshop.daos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    @Test
    void getInstance() {
        DatabaseConnector a = DatabaseConnector.getInstance();
        assertEquals(DatabaseConnector.single_instance, a);
    }

}