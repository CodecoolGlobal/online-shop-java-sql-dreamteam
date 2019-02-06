package com.codecool.onlineshop.views;

import java.util.Scanner;

public class MainView {
    protected Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }


    public void printMenu() {
        println("Welcome to online shop" + "(1) Log in" + "(2) Create new user" + "(3) Exit\n");
    }

    public void userMenu(){
        println("(1) Show basket" + "(2) Add product to basket" + "(3) Edit product quantity" + "(4) Remove a product" +
                "(5) Place an order" + "(6) See my previous orders" + "(7) List available products" +
                "(8) List category-based products" + "(9) Check product availability");
    }

    public void adminMenu(){
        println("(1) Create new category" + "(2) Edit category name" + "(3) Add new product" + "(4) Edit a product" +
                "(5) Deactivate a product" + "(6) Make a discount");
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
}
