package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.*;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    UserDao userDao;
    ProductDao productDao;
    AdminService adminService;

    @BeforeEach
    void setup() {
        User admin = new Customer("kamil", "bed");
        userDao = new UserDaoStub();
        productDao = new ProductDaoStub();
        adminService = new AdminService(admin, productDao, userDao);
    }

    @Test
    void editCategoryNameIfCategoryExists () {
        adminService.editCategoryName("fruits", "large fruits");
        assertDoesNotThrow(() -> productDao.getCategoryIdByName("large fruits"));
    }

    @Test
    void failToEditCategoryNameIfCategoryNotExists() {
        try {
            List<String > pre = productDao.getAllCategoryNames();
            adminService.editCategoryName("orara", "olaga");
            List<String> post = productDao.getAllCategoryNames();
            assertEquals(pre, post);
        } catch (DAOException e) {
            //wont happen
        }

    }

    @Test
    void failToEditCategoryNameIfNewNameAlreadyInCategories() {
        try {
            adminService.editCategoryName("fruits", "diary");
            List<String> post = productDao.getAllCategoryNames();
            assertDoesNotThrow(() -> productDao.getCategoryIdByName("fruits"));
        } catch (DAOException e) {
            //wont happen
        }

    }

    @Test
    void addNewCategoryIfCategoryOfThatNameNotExists() {
        adminService.addNewCategory("sweets");
        try {
            assertTrue(productDao.getAllCategoryNames().contains("sweets"));
        } catch (DAOException e) {
            //wont happen
        }

    }

    @Test
    void DoNotAddCatagoryIfCategoryOfThatNameExists() {
        adminService.addNewCategory("fruits");
        try {
            assertEquals(1, productDao.getAllCategoryNames().stream().filter(s -> s.equals("fruits")).count());
        } catch (DAOException e) {
            //wont happen
        }
    }
}