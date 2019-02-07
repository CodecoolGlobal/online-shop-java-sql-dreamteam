package com.codecool.onlineshop.daos;

public class DAOException extends Exception{
    
    public DAOException(String message) {
        super(message);
    }

    public DAOException() {
        super();
    }
}