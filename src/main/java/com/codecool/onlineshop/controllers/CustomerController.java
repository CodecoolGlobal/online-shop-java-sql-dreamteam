package com.codecool.onlineshop.controllers;

import java.util.Iterator;

import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.services.CustomerService;
import com.codecool.onlineshop.views.MainView;

public class CustomerController {
    private MainView mainView;
    private CustomerService service;

    public CustomerController() {
        this.mainView = new MainView();
        this.service = new CustomerService();
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
                    printBasket();
                    break;
                case 2:
                    //Add to busket
                    printAvailableProducts();
                    mainView.print("Enter product number");
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

    private void printBasket() {
        Iterator busket = service.getBusketIterator();
        int i = 1;
        while(busket.hasNext()) {
            mainView.print(i + ". ");
            mainView.println(((Product) busket.next()).productToString());
        }
    }
}