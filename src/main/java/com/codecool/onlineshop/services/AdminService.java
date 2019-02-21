package com.codecool.onlineshop.services;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.models.User;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    User admin;
    private ProductDao productDao;
    private UserDao userDao;


    public AdminService(User admin, ProductDao productDao, UserDao userDao) {
        this.admin = admin;
        this.userDao = userDao;
        this.productDao = productDao;
    }


    public void addNewCategory(String name) {
        try {
            productDao.getCategoryIdByName(name);
            System.out.println("There is already that category in database!");
        } catch (DAOException e) {
            try {
                productDao.addNewCategory(name);
                System.out.println("Category has been added.");
            } catch (DAOException f) {
                System.out.println("Something went wrong");
            }
        }
    }


    public void addNewProduct(List<String> newProductData, int categoryId) {
        try {
            productDao.addNewProduct(newProductData, categoryId);
        } catch (DAOException e) {
            System.out.println("Something went wrong.");
        }

    }


    public boolean checkIfProductInDataBase(String name) {
        name.toLowerCase();
        try {
            if (productDao.checkIfAvailableProduct(name) == true) {
                return true;
            }

        } catch (DAOException e) {
            System.out.println("Something went wrong!");
        }
        return false;
    }


    public List<String> getCategoryNames() {
        List<String> categoryNames = new ArrayList<>();
        try {
            categoryNames = productDao.getAllCategoryNames();
            return categoryNames;
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
        return categoryNames;
    }


    public void deactivateProduct(String name) {
        try {
            productDao.deactivateProduct(name);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    public List<Product> getListOfProducts() throws ServiceException {
        try {
            return productDao.getAllProducts();
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }


    public void editCategoryName(String oldName, String newName){
        try {
            productDao.getCategoryIdByName(newName);
            System.out.println("There is already that category in database!");
        } catch (DAOException e) {
            try {
                List<String> categoryNames = getCategoryNames();
                if (categoryNames.contains(oldName)){
                    productDao.updateCategoryName(oldName, newName);
                    System.out.println("Category has been changed! ");
                }
                else{
                    System.out.println("There is no such category to update!");
                }   
            } catch (DAOException f) {
                System.out.println("Something went wrong");
            }
        }
    }

    public void editProductName(String productName, String newName){
        try {
                productDao.updateProductName(productName, newName);
                System.out.println("Product name has been changed! ");
        } catch (DAOException f) {
            System.out.println("Something went wrong");
        }
    }
    public void editProductPrice(String productName, double newPrice){
        try {
            productDao.updateProductPrice(productName, newPrice);
            System.out.println("Product name has been changed! ");
        } catch (DAOException f) {
            System.out.println("Something went wrong");
        }
    }
    public void editProductQuantity(String productName, int newQuantity){
        try {
            productDao.updateProductQuantity(productName, newQuantity);
            System.out.println("Product name has been changed! ");
        } catch (DAOException f) {
            System.out.println("Something went wrong");
        }
    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>();
        try {
            orders = userDao.getAllOrders();
        } 
        catch (DAOException e) {
            System.out.println("Something went wrong. ");
        }
        return orders;
    }

    public void changeStatuses() throws DAOException{
        userDao.changeStatusesOfOrders();
    }


}




            






