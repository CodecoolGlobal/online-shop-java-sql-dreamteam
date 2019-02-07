package com.codecool.onlineshop.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.services.CustomerService;
import com.codecool.onlineshop.services.ServiceException;
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
                    addProductToBasket();
                    
                    break;
                case 3:
                    editProductQuantity();
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

    private void addProductToBasket() {
        List<Product> availableProducts = null;
        try {
            availableProducts = service.getAllProducts();
        } catch (ServiceException e) {
            mainView.println("Cannot print available products");
        }

        Iterator<Product> products = availableProducts.iterator();
        int i = 1;
        while(products.hasNext()) {
            mainView.print(i + ". ");
            mainView.println(products.next().productToString());
            i++;
        }

        if (availableProducts == null || availableProducts.isEmpty()) {
            return;
        }

        mainView.println("Enter product number: ");
        int number = mainView.getIntegerInput() - 1;
        Product product = null;
        if (number < 0 || number >= availableProducts.size()) {
            mainView.println("Wrong product number.");
            mainView.getEmptyInput();
            return;
        } else {
            product = availableProducts.get(number);
        }
        mainView.println("Enter amount: ");
        int amount = mainView.getIntegerInput();
        if (amount < 1 || amount > product.getAmount()) {
            mainView.println("Wrong amount.");
            mainView.getEmptyInput();
            return;
        } else {
            service.addProduct(product, amount);
            mainView.print("Product added successfuly.");
            mainView.getEmptyInput();
        }      
    }

    private void printAvailableProducts(){

    }

    private void printBasket() {
        Iterator busket = service.getBusketIterator();
        int i = 1;
        while(busket.hasNext()) {
            mainView.print(i + ". ");
            mainView.println(((Product) busket.next()).productToString());
            i++;
        }
    }
}