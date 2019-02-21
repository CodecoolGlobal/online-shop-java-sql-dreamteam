package com.codecool.onlineshop.services;

import com.codecool.onlineshop.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codecool.onlineshop.containers.*;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.*;
import com.codecool.onlineshop.services.*;

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

    public void addNewFeaturedCategory(String name, Date expirationDate){
        try {
            productDao.getFeaturedCategoryIdByName(name);
            System.out.println("There is already that category in database!");
        } catch (DAOException b) {
            try {
                productDao.getCategoryIdByName(name);
                System.out.println("There is already that category in database!");
            } catch (DAOException e) {
                try {
                    productDao.addNewFeaturedCategory(name, expirationDate);
                    System.out.println("Category has been added.");
                } catch (DAOException f) {
                    f.printStackTrace();
                    System.out.println("Something went wrong");
                }
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

    public List<String> getFeaturedCategoryNames() {
        List<String> names = new ArrayList<>();
        try{
            names = productDao.getAllFeaturedCategoryNames();
            return names;
        } catch (Exception e){
            System.out.println("Something went wrong!");
        }
        return names;
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
            e.printStackTrace();
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

    public void featureProduct(String categoryName, String productName){

        try{
            if (productDao.getFeaturedCategoryIdByName(categoryName) != null ) {
                if (productDao.getProductByName(productName) != null){

                    productDao.featureProduct(productDao.getFeaturedCategoryIdByName(categoryName), productName);
                    System.out.println("Product has been featured!");
                } else{ System.out.println("Product doesnt exist"); }
            } else{ System.out.println("Category doesnt exist");}
        } catch (Exception e){

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


    public void updateFeatured() {
        try {
            productDao.updateFeatured();
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

}




            






