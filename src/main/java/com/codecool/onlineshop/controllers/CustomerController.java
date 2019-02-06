package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.views.MainView;

public class CustomerController {
    private MainView mainView;

    public CustomerController() {
        this.mainView = new MainView();
    }

    public void run() {
        mainView.clearScreen();
        int choice = -1;
        while (choice != 0) {
            mainView.printCustomerMenu();
            choice = mainView.getIntegerInput();
            switch (choice) {
                case 1:
                    // Show basket
                    break;
                case 2:
                    // Add product to basket
                    break;
                case 3:
                    // Edit product quantity
                    break;
                case 4:
                    // Remove a product
                    break;
                case 5:
                    // Place an order
                    break;
                case 6:
                    // See previous orders
                    break;
                case 7:
                    // List available products
                    break;
                case 8:
                    // List category-based products
                    break;
                case 9:
                    // Check product availability
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