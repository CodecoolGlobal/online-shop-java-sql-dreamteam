package com.codecool.onlineshop.services;

public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super();
    }

}