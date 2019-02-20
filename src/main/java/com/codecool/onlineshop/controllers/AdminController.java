package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.User;
import com.codecool.onlineshop.services.AdminService;
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
                    getAllOrders();
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
        mainView.println("Product edited");
    }
    private void printListOfCurrentProducts() {
        try {
            mainView.printListOfProducts(adminService.getListOfProducts());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void getAllOrders(){
        List<Order> orders = adminService.getAllOrders();
        mainView.printAllOrders(orders);
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
