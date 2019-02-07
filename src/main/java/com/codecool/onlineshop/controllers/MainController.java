package com.codecool.onlineshop.controllers;

import javax.swing.text.View;

import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.Admin;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.User;
import com.codecool.onlineshop.views.MainView;
import com.codecool.onlineshop.services.*;

public class MainController {
    private MainView mainView;
    private User user;

    public MainController(){
        this.mainView = new MainView();
    }

    public void run() {
        mainView.clearScreen();
        int choice = -1;
        while (choice != 0) {
            mainView.printMenu();
            choice = mainView.getIntegerInput();
            switch (choice) {
                case 1:
                    // Log in
                    user = login();
                    if (user == null) {
                        mainView.print("Your login or password is wrong. Try again");
                        mainView.getEmptyInput();
                    } else {
                        if (user instanceof Admin) {
                            handleAdmin();
                        } else {
                            handleCustomer();
                        }
                    }
                    
                    break;
                case 2:
                    // Create new user
                    break;
                case 0:
                    choice = 0;
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }

    private void handleAdmin() {
        Admin admin = (Admin) user;
        mainView.print("Im admin");
        AdminController adminController = new AdminController(user);
        adminController.run();
    }

    private void handleCustomer() {
        Customer customer = (Customer) user;
        mainView.print("Im customer");
        CustomerController controller = new CustomerController();
        controller.run();
    }

    private User login() {
        mainView.println("Enter user login:");
        String login = mainView.getStringInput().trim();
        mainView.println("Enter password:");
        String password = mainView.getStringInput().trim();
        UserDao userDao = new UserDao();
        User user = null;
        try {
            user = userDao.getUser(login, password);
        } catch (DAOException e) {
            mainView.print(e.getMessage());
        }
        return user;
    }
}
