package com.codecool.onlineshop.services;

import com.codecool.onlineshop.models.*;

import java.util.ArrayList;
import java.util.List;

import com.codecool.onlineshop.containers.*;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.*;
import com.codecool.onlineshop.services.*;

public class AdminService extends Service {
    User admin;


    public AdminService(User admin) {
        this.admin = admin;
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


}




            






