package com.codecool.onlineshop.controllers;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.services.CustomerService;
import com.codecool.onlineshop.services.ServiceException;
import com.codecool.onlineshop.views.MainView;

import java.util.*;

public class CustomerController {
    private MainView mainView;
    private CustomerService service;

    public CustomerController(Customer customer) {
        ProductDao productDao = new ProductDao();
        UserDao userDao = new UserDao();
        this.mainView = new MainView();
        this.service = new CustomerService(customer, productDao, userDao );
    }

    public void run() {
        mainView.clearScreen();
        changeStatusesOfOrders();
        rateProducts();
        int choice = -1;
        while (choice != 0) {
            changeStatusesOfOrders();
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
                    checkIfProductExists();
                    break;

                case 10:
                    // pay for order
                    payForOrder();
                    break;
                case 0:
                    choice = 0;
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }



    private void payForOrder(){
        showPreviousOrders();
        mainView.println("Which order you want to pay?");
        int orderId = mainView.getIntegerInput();
        try{
            service.payForOrder(orderId);
            mainView.println("Order has been paid.");
        }
        catch (DAOException | ServiceException e){
            mainView.println(e.getMessage());
        }

    }


    private void checkIfProductExists() {
        mainView.println("Find product availability\nEnter product name:");
        String name = mainView.getStringInput().trim().toLowerCase();
        try {
            Product product = service.getProductByName(name);
            mainView.println("-----------------");
            mainView.println("Product found: ");
            mainView.println(product.productToString());
        } catch (DAOException e) {
            mainView.println(e.getMessage());
        } catch (ServiceException e) {
            mainView.println(e.getMessage());
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


    private void showUnpaidOrders(){
        try {
            List<Order> orders = service.getCustomerOrders();
            mainView.println("------------------");
            mainView.println("Your orders:");
            for (int i = 0; i < orders.size(); i++){
                if(orders.get(i).getStatus().equals("submited")){
                mainView.print(i + ". ");
                mainView.println(orders.get(i).toString());
            }
        }}
            catch (DAOException e) {
            mainView.println("You have paid for all of your orders.");
        }
    }



    private void placeOrder() {
        try {
            service.placeAnOrder();
        } catch (DAOException | ServiceException e) {
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

    private void changeStatusesOfOrders(){
        try{
            service.changeStatuses();
        }
        catch (DAOException e){
            mainView.println("Statuses cannot be changed");
        }
    }

    private void rateProducts(){
        try{
            Set<Product> products = service.getDeliveredProducts();
            List<Order> orders = service.getUnratedAndDeliveredOrders();
            Map<String, Integer> rates = new HashMap<>();
            if(!orders.isEmpty()){
                for (Order order: orders) {
                    for (Product product: order.getBasket().getProducts()) {
                        mainView.println("Type rate (1-5) for:  " + product.getName());
                        int rate = mainView.getRateInput();
                        rates.put(product.getName(), rate);
                    }
                    service.updateProductsRatings(rates);
                    service.setOrdersAsRated();
                    mainView.println("Thank you!");
                }
            }
        }
        catch (DAOException e){
            mainView.println("something went wrong");
        }
    }

}