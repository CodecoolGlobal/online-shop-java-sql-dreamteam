package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.User;
import com.codecool.onlineshop.services.AdminService;

import com.codecool.onlineshop.containers.*;
import com.sun.xml.internal.bind.v2.TODO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.codecool.onlineshop.services.ServiceException;
import com.codecool.onlineshop.views.MainView;


import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private MainView mainView;
    User admin;
    AdminService adminService;


    public AdminController(User admin) {
        this.mainView = new MainView();
        this.admin = admin;
        ProductDao productDao = new ProductDao();
        UserDao userDao = new UserDao();
        this.adminService = new AdminService(admin, productDao, userDao);
    }

    public void run() {
        mainView.clearScreen();
        int choice = -1;
        while (choice != 0) {
            changeStatusesOfOrders();
            mainView.printAdminMenu();
            choice = mainView.getIntegerInput();
            adminService.updateFeatured();
            switch (choice) {
                case 1:
                    addNewCategory();
                    break;
                case 2:
                    editCategoryName();
                    break;
                case 3:
                    addNewProduct();
                    break;

                case 4:
                    editProduct();
                    break;
                case 5:
                    deactivateProduct();
                    break;
                case 6:
                    showAllOrders();
                    break;
                case 7:
                    //add new featured category
                    addNewFeaturedCategory();
                    break;
                case 8:
                    //feature a product
                    featureProduct();
                    break;
                case 0:
                    choice = 0;
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }

    private void addNewCategory(){
        mainView.println("These are available categories: \n");
        mainView.printStringTable(adminService.getCategoryNames());
        mainView.println("\nType name of new category: ");
        String categoryName = mainView.getStringInput();
        adminService.addNewCategory(categoryName.toLowerCase());
        mainView.getEmptyInput();
    }

    private void addNewFeaturedCategory(){
        mainView.println("Type name of new category: ");
        String categoryName = mainView.getStringInput();
        mainView.println("Please provide expiration date in the following format");
        mainView.println("[ yyyy-mm-dd ]");
        String strExpirationDate = mainView.getStringInput();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date expirationDate = dateFormat.parse(strExpirationDate);
            //String strDate = dateFormat.format(strExpirationDate);
            adminService.addNewFeaturedCategory(categoryName.toLowerCase(), expirationDate);
            mainView.getEmptyInput();
        }
        catch (ParseException e) {
            System.out.println("ParseError " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addNewProduct(){
        List<String> attributesOfProduct = new ArrayList<>();

        if(adminService.getCategoryNames().size() == 0){
            mainView.println("There are no categories created yet! You need to create one.");
            addNewCategory();
        }

        int categoryId = getCategory();
        String productName = getProductName();
        attributesOfProduct.add(productName);

        mainView.println("Type price of product: ");
        Float price = mainView.getFloatInput();
        attributesOfProduct.add(price.toString());

        mainView.println("Type amount of product: ");
        Integer amount = mainView.getIntegerInput();
        attributesOfProduct.add(amount.toString());

        mainView.println("Type availability of product: ");
        Boolean availability = mainView.getBooleanInput();
        attributesOfProduct.add(availability.toString());

        adminService.addNewProduct(attributesOfProduct, categoryId);
        mainView.println("Product has been added! ");
        mainView.getEmptyInput();
    }


    private int getCategory(){
        mainView.printStringTable(adminService.getCategoryNames());
        mainView.println("\nPick category which you want add product to: ");
        int categoryId = mainView.getIntegerInput();

        while(categoryId < 1 || categoryId > adminService.getCategoryNames().size()){
            mainView.println("Wrong input. Try again.");
            categoryId = mainView.getIntegerInput();
        }
        return categoryId;
    }

    private String getProductName(){
        mainView.println("Type name of new product: ");
        String productName = mainView.getStringInput().toLowerCase();
        Boolean isProductNameAvailable = adminService.checkIfProductInDataBase(productName); 

        while (isProductNameAvailable == false) {
            mainView.println("There is already that product in database! Try another one: ");
            productName = mainView.getStringInput();
            isProductNameAvailable = adminService.checkIfProductInDataBase(productName); 
        }
        return productName;
    }


    private void editCategoryName(){
        mainView.println("These are available categories: \n");
        mainView.printStringTable(adminService.getCategoryNames());
        mainView.println("\nChoose which category you want to edit(by its name): ");
        String oldName = mainView.getStringInput();
        mainView.println("Write new name of category: ");
        String newName = mainView.getStringInput();
        adminService.editCategoryName(oldName, newName);
        mainView.getEmptyInput();
    }

    private void deactivateProduct() {
        printListOfCurrentProducts();
        mainView.println("Choose product to deactivate: ");
        adminService.deactivateProduct(mainView.getStringInput());
        mainView.clearScreen();
        mainView.println("Product deactivated\n");
    }

    private void featureProduct(){
        mainView.printStringTable(adminService.getFeaturedCategoryNames());
        mainView.println("Choose featured category: ");
        String categoryName = mainView.getStringInput();

        mainView.clearScreen();

        printListOfCurrentProducts();
        mainView.println("Choose product for discount: ");
        String productName = mainView.getStringInput();
;
        adminService.featureProduct(categoryName, productName);
        mainView.clearScreen();
    }

    private void editProduct(){
        printListOfCurrentProducts();
        mainView.println("Choose product to edit: ");
        String productName = mainView.getStringInput();
        mainView.print("Choose argument to edit (0 to skip): \n(1) Name \n(2) Price \n(3) Quantity\n");
        int argument = -1;
        while(argument < 0 || argument > 3){
                argument = mainView.getIntegerInput();
        }

        mainView.println("Provide new value: ");

        if(argument == 1){
            adminService.editProductName(productName, mainView.getStringInput());
        }
        else if(argument == 2){
            adminService.editProductPrice(productName,mainView.getDoubleInput());
        }
        else if(argument == 3){
            adminService.editProductQuantity(productName, mainView.getIntegerInput());
        }
        mainView.clearScreen();
        mainView.println("Product edited\n");
    }
    private void printListOfCurrentProducts() {
        try {
            mainView.printListOfProducts(adminService.getListOfProducts());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void showAllOrders() {
            List<Order> orders = adminService.getAllOrders();
            mainView.println("------------------");
            for (int i = 0; i < orders.size(); i++){
                mainView.print(i + ". ");
                mainView.println(orders.get(i).toString());
            }
    }

    private void changeStatusesOfOrders(){
        try{
            adminService.changeStatuses();
        }
        catch (DAOException e){
            mainView.println("Statuses cannot be changed");
        }
    }




}
