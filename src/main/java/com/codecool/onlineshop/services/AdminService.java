package com.codecool.onlineshop.services;

import com.codecool.onlineshop.models.*;
import java.util.List;
import com.codecool.onlineshop.containers.*;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.*;

public class AdminService extends Service{
    User admin;



    public AdminService(User admin){
        this.admin = admin;
    }


    public void addNewCategory(String name){
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
    }}


    public void addNewProduct(String name){
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










}
