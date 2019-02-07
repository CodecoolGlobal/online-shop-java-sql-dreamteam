package com.codecool.onlineshop.services;

import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CustomerService extends Service {
    private Customer customer;

    public CustomerService(Customer customer) {
        customer = customer;
    }

    public Iterator getBusketIterator() {
        return customer.getBasket().getIterator();
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getCustomerPassword() {
        return customer.getName();
    }

    public boolean isBasketEmpty() {
        return customer.getBasket().getIterator().hasNext() ? false : true;
    }

    public int getBasketSize() {
        return customer.getBasket().getBasketSize();
    }

    public void removeProductFromBasket(Product product) {
        customer.getBasket().deleteProduct(product);
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

    public void placeAnOrder() throws DAOException {
        userDao.addOrder(getCustomerName(), "submited", new Date());
        Iterator<Product> basket = customer.getBasket().getIterator();
        while (basket.hasNext()) {
            Product basketProduct = basket.next();
            System.out.println("before");
            Product product = productDao.getProductById(basketProduct.getId());
            System.out.println("after");
            int newAmount = product.getAmount() - basketProduct.getAmount();
            List<String> editData = Arrays.asList(basketProduct.getName(),
                    Double.toString(basketProduct.getPrice()),
                    Integer.toString(newAmount),
                    Boolean.toString(basketProduct.isAvailable()));
            editData.forEach(s -> System.out.println(s));
            productDao.editProduct(basketProduct.getId(), editData, basketProduct.getCategory().getId());
        }
    }
}