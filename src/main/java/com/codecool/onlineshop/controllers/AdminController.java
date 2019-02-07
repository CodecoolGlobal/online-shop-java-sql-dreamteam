package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.views.MainView;
import com.codecool.onlineshop.models.User;
import com.codecool.onlineshop.services.AdminService;
import java.util.Scanner;

public class AdminController {
    private MainView mainView;
    User admin;
    AdminService adminService;
    Scanner input = new Scanner(System.in);

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
                    String categoryName = input.nextLine();
                    adminService.addNewCategory(categoryName.toLowerCase());
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
