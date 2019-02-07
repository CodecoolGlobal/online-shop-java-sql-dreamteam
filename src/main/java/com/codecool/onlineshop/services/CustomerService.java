package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CustomerService extends Service {
    private Customer customer = new Customer("login", "password");

    public Iterator getBusketIterator() {
        return customer.getBasket().getIterator();
    }

    public boolean isBasketEmpty() {
        return customer.getBasket().getIterator().hasNext() ? false : true;
    }

    public int getBasketSize() {
        return customer.getBasket().getBasketSize();
    }

    public Product getProductFromBasket(int productNumber) {
        if (productNumber < 0 || productNumber >= getBasketSize()) {
            return null;
        }
        Iterator<Product> basket = customer.getBasket().getIterator();
        int i = 0;
        while (basket.hasNext()) {
            Product product = basket.next();
            if (i == productNumber) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(Product product, int amount) {
        Product productToAdd = new Product(product.getId(), product.getName(), product.getPrice(), amount, product.isAvailable(), product.getCategory());
        customer.getBasket().addProduct(productToAdd);
    }

    public void deleteProductFromBasket(Product basketPproduct) {
            customer.getBasket().deleteProduct(basketPproduct);
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

    public Product getProductById(int id) {
        try {
            return productDao.getProductById(id);
        } catch (DAOException e) {
            
        }
        return null;
    }

    public void placeAnOrder() {
        //...
    }
}