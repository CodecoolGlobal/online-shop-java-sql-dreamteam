package com.codecool.onlineshop.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.services.CustomerService;
import com.codecool.onlineshop.services.ServiceException;
import com.codecool.onlineshop.views.MainView;

public class CustomerController {
    private MainView mainView;
    private CustomerService service;

    public CustomerController(Customer customer) {
        this.mainView = new MainView();
        this.service = new CustomerService(customer);
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
                    deleteProductFromBasket();
                    break;
                case 5:
                    // Place an order
                    placeOrder();
                    break;
                case 6:
                    // See previous orders
                    showPreviousOrders();
                    break;
                case 7:
                    // List available products
                    printAvailableProducts();
                    break;
                case 8:
                    // List category-based products
                    listProductsByCategory();
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

    private void listProductsByCategory() {
        mainView.println("Enter category name");
        String name = mainView.getStringInput().trim().toLowerCase();
        try {
            List<Product> products = service.getProductByCategory(name);
            mainView.println("-----------------");
            mainView.println("Products: ");
            int i = 1;
            for (Product product : products) {
                mainView.print(i + ". ");
                mainView.println(product.productToString());
            }
        } catch (DAOException e) {
            mainView.println(e.getMessage());
        }

    }

    private void showPreviousOrders() {
        try {
            List<Order> orders = service.getCustomerOrders();
            mainView.println("------------------");
            mainView.println("Your orders:");
            for (int i = 0; i < orders.size(); i++){
                mainView.print(i + ". ");
                mainView.println(orders.get(i).toString());
            }
        } catch (DAOException e) {
            mainView.println("Cannot get orders");
        }
    }

    private void placeOrder() {
        try {
            service.placeAnOrder();
        } catch (DAOException e) {
            mainView.println("Cannot place an order");
        }
    }

    private void deleteProductFromBasket() {
        printBasket();

        mainView.println("Enter basket product number: ");
        int number = mainView.getIntegerInput() - 1;
        Product product = service.getProductFromBasket(number);
        if (product == null) {
            mainView.println("Wrong product number.");
            mainView.getEmptyInput();
            return;
        }

        service.deleteProductFromBasket(product);
        mainView.println("Product Deleted successfuly.");
        mainView.getEmptyInput();
    }

    private void editProductQuantity() {
        
        printAvailableProducts();
        
        printBasket();
        if (service.isBasketEmpty()) {
            mainView.println("There is no products in basket");
            return;
        }

        mainView.println("Enter basket product number: ");
        int number = mainView.getIntegerInput() - 1;
        Product product = service.getProductFromBasket(number);
        if (product == null) {
            mainView.println("Wrong product number.");
            mainView.getEmptyInput();
            return;
        }
        mainView.println("Enter amount: ");
        int amount = mainView.getIntegerInput();
        if (amount < 1 || amount > service.getProductById(product.getId()).getAmount()) {
            mainView.println("Wrong amount.");
            mainView.getEmptyInput();
            return;
        } else {
            product.setAmount(amount);
            mainView.println("Product Edited successfuly.");
            mainView.getEmptyInput();
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
            mainView.println("Product added successfuly.");
            mainView.getEmptyInput();
        }      
    }

    private void printAvailableProducts(){
        mainView.println("------------------");
        mainView.println("All product:");
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
    }

    private void printBasket() {
        mainView.println("------------------");
        mainView.println("Basket products:");
        Iterator busket = service.getBusketIterator();
        int i = 1;
        while(busket.hasNext()) {
            mainView.print(i + ". ");
            mainView.println(((Product) busket.next()).productToString());
            i++;
        }
    }
}