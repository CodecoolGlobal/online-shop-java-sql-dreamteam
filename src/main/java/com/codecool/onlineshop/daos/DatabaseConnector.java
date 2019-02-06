package com.codecool.onlineshop.daos;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    protected static DatabaseConnector single_instance = null;
    Connection c;

    private DatabaseConnector(){

    }

    static DatabaseConnector getInstance() {
        if (single_instance == null)
            single_instance = new DatabaseConnector();

        return single_instance;
    }

    void connectToDatabase() {
        c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:onlineShop.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Connection getConnection(){
        return c;
    }
}
