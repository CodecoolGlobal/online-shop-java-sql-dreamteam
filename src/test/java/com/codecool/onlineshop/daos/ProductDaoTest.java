package com.codecool.onlineshop.daos;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    static ProductDao productDao = new ProductDao();

    @BeforeAll
    static void initAll() {
        List<String> productData = new ArrayList<>();
        productData.add("mama");
        productData.add("1");
        productData.add("1");
        productData.add("true");
        productData.add("1");
        productData.add("1");
        try {
            productDao.addNewCategory("dirt");
            int categoryId = productDao.getCategoryIdByName("dirt");
            productDao.addNewProduct(productData, categoryId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addNewProduct() {
        try {
            assertEquals(productDao.getAllProducts().size(), 1);
        } catch (DAOException e) {
            System.out.println("addNewProduct test failed");
            e.printStackTrace();
        }
    }

    @Test
    void getAvailableProducts() {
        try {
            assertTrue(productDao.getAvailableProducts().get(0).isAvailable());
        } catch (DAOException e) {
            System.out.println("getAvailableProducts test failed");
            e.printStackTrace();
        }
    }

    @Test
    void getProductById() {
        try {
            assertEquals(productDao.getProductById(1).getName(), productDao.getAllProducts().get(0).getName());
        } catch (DAOException e) {
            System.out.println("getProductById test failed");
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDownAll() {
        File file = new File("onlineShop.db");
        file.delete();
    }

}