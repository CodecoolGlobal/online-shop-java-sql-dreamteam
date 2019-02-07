package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.views.MainView;
import com.codecool.onlineshop.models.User;
import com.codecool.onlineshop.services.AdminService;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminController {
    private MainView mainView;
    User admin;
    AdminService adminService;


    public AdminController(User admin){
        this.mainView = new MainView();
        this.admin = admin;
        this.adminService = new AdminService(admin);
    }

    public void run() {
        mainView.clearScreen();
        int choice = -1;
        while (choice != 0) {
            mainView.printAdminMenu();
            choice = mainView.getIntegerInput();
            switch (choice) {
                case 1:
                    mainView.println("Type name of new category: ");
                    String categoryName = mainView.getStringInput();
                    adminService.addNewCategory(categoryName.toLowerCase());
                    break;
                case 2:
                    // Edit category name
                    break;
                case 3:
                    List<String> attributesOfProduct = new ArrayList<>();
                    mainView.println("Type name of new product: ");
                    String productName = mainView.getStringInput();
                    Boolean isProductNameAvailable = adminService.checkIfProductInDataBase(productName); 

                    while (isProductNameAvailable == false) {
                        mainView.println("There is already that product in database! Try another one: ");
                        productName = mainView.getStringInput();
                        isProductNameAvailable = adminService.checkIfProductInDataBase(productName); 
                    }
                    attributesOfProduct.add(productName);

                    mainView.println("Type amount of product: ");
                    Float amount = mainView.getFloatInput();
                    attributesOfProduct.add(amount.toString());

                    mainView.println("Type price of product: ");
                    Integer price = mainView.getIntegerInput();
                    attributesOfProduct.add(price.toString());

                    mainView.println("Type availability of product: ");
                    Boolean availability = mainView.getBooleanInput();
                    attributesOfProduct.add(availability.toString());

                    mainView.println("Pick category which you want add product to: ");
                    mainView.printStringTable(adminService.getCategoryNames());
                    int categoryId = mainView.getIntegerInput();
                    System.out.println(attributesOfProduct.toString());
                    adminService.addNewProduct(attributesOfProduct, categoryId);
                    break;

                case 4:
                    // Edit product
                    break;
                case 5:
                    // Deactivate a product
                    break;
                case 6:
                    // Make a discount
                    break;
                case 0:
                    choice = 0;
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }






}
