package com.codecool.onlineshop.views;

import com.codecool.onlineshop.models.Product;

import java.util.Scanner;
import java.util.List;
import com.codecool.onlineshop.containers.*;

public class MainView {
    protected Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }


    public void printMenu() {
        println("Welcome to online shop \n" + "(1) Log in \n" + "(2) Create new user\n" + "(0) Exit \n");
    }

    public void printCustomerMenu() {
        println("(1) Show basket \n" + "(2) Add product to basket \n" + "(3) Edit product quantity \n" +
                "(4) Remove a product \n" + "(5) Place an order \n" + "(6) See my previous orders \n" +
                "(7) List available products \n" + "(8) List category-based products \n" +
                "(9) Check product availability \n" + "(0) Exit \n");

    }

    public void printAdminMenu() {
        println("(1) Create a new category \n" + "(2) Edit category name \n" + "(3) Add new product \n" +
                "(4) Edit a product \n" + "(5) Deactivate a product \n" + "(6) Show all orders \n" +
                "(7) Set a discount" + "(0) Exit \n");
    }

    public int getIntegerInput() {
        while (!scanner.hasNextInt()) {
            println("Wrong input. Integer required.");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            println("Wrong input. Double required.");
            scanner.nextLine();
        }
        double number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public boolean getBooleanInput() {
        while (!scanner.hasNextBoolean()) {
            println("Wrong input. true or false required.");
            scanner.nextLine();
        }
        boolean availability = scanner.nextBoolean();
        scanner.nextLine();
        return availability;
    }

    public float getFloatInput() {
        while (!scanner.hasNextFloat()) {
            println("Wrong input. Float required.");
            scanner.nextLine();
        }
        float number = scanner.nextFloat();
        scanner.nextLine();
        return number;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String getEmptyInput() {
        println("Press enter to continue: ");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            clearScreen();
            return "-1";
        }
        clearScreen();
        return "-1";

    }

    public String getStringInput() {
        String string = scanner.nextLine();
        return string;
    }


    public void printStringTable(List<String> list) {
        for (String item : list) {
            System.out.println(item);
        }
    }


    public int getIntegerInputInParition(int range) {
        while (!scanner.hasNextInt()) {
            println("Wrong input. Integer required.");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public void printListOfProducts(List<Product> listOfProducts) {
        for (Product p : listOfProducts) {
            System.out.println(p.productToString());
        }
    }

    public void printAllOrders(List<Order> orders){
        System.out.printf("%-15s%-15s%-30s%-15s%-15s%-15s\n", "Id", "User Name", "Order created at", "Status", "Payment", "Order paid at" );
        for (Order order : orders) {
            System.out.printf("%-15s%-15s%-30s%-15s%-15s%-15s\n", order.getId(), order.getUser().getName(), order.getOrderCreatedAt(), order.getStatus(), order.getPaid(), order.getOrderPayAt());

        }
    }


} 
