package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;

import java.util.Iterator;
import java.util.List;

public class CustomerService extends Service {
    private Customer customer = new Customer("login", "password");

    public void addProduct(Product product, int amount) {
        Product productToAdd = new Product(product.getId(), product.getName(), product.getPrice(), amount, product.isAvailable(), product.getCategory());
        customer.getBasket().addProduct(productToAdd);
    }

    public void deleteProductFromBasket(int productId) {
        try {
            Product product = productDao.getProductById(productId);
            customer.getBasket().deleteProduct(product);
        } catch (DAOException e) {

        }
    }

    public void editNumberOf(int productId, int amount) {
        Iterator iterator = customer.getBasket().getIterator();

        while (iterator.hasNext()) {
            Product product = (Product) iterator.next();
            if (product.getId() == productId) {
                product.setAmount(amount);
            }
        }
    }

    public List<Product> getAllProducts() throws ServiceException{
        try {
            return productDao.getAvailableProducts();
        } catch (DAOException e ) {
            throw new ServiceException();
        }
    }

    public void placeAnOrder() {
        //...
    }
}