package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.ProductDaoStub;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminServiceTest {

    UserDao userDao;
    ProductDao productDao;
    AdminService adminService;

    @BeforeEach
    void setup() {
        User admin = new Customer("kamil", "bed");
        userDao = null; //until stub is created
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
}