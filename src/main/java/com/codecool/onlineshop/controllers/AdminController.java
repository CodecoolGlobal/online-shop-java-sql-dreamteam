package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.views.MainView;

public class AdminController {
    private MainView mainView;

    public AdminController(){
        this.mainView = new MainView();
    }

    public void run() {
        mainView.clearScreen();
        int choice = -1;
        while (choice != 0) {
            mainView.printAdminMenu();
            choice = mainView.getIntegerInput();
            switch (choice) {
                case 1:
                    // Create new category
                    break;
                case 2:
                    // Edit category name
                    break;
                case 3:
                    // Add new product
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
