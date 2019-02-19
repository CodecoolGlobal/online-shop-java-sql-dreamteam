package com.codecool.onlineshop.services;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CustomerService  {
    private Customer customer;
    private ProductDao productDao;
    private UserDao userDao;

    public CustomerService(Customer customer, ProductDao productDao, UserDao userDao) {
        this.customer = customer;
        this.productDao = productDao;
        this.userDao = userDao;
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
            Product product = productDao.getProductById(basketProduct.getId());
            int newAmount = product.getAmount() - basketProduct.getAmount();
            List<String> editData = Arrays.asList(basketProduct.getName(),
                    Double.toString(basketProduct.getPrice()),
                    Integer.toString(newAmount),
                    Boolean.toString(basketProduct.isAvailable()));
            productDao.editProduct(basketProduct.getId(), editData, basketProduct.getCategory().getId());
        }
        customer.getBasket().deleteAllProducts();
    }

    public List<Order> getCustomerOrders() throws DAOException{
        List<Order> orders = userDao.getOrdersByUserName(customer.getName());
        return orders;
    }

    public List<Product> getProductByCategory(String categoryName) throws DAOException {
        List<Product> products = productDao.getAvailableProducts();
        int categoryId = productDao.getCategoryIdByName(categoryName);
        products = products.stream().filter(p -> p.getCategory().getId() == categoryId).collect(Collectors.toList());
        return products;
    }

    public Product getProductByName(String productName) throws DAOException, ServiceException{
        List<Product> products = productDao.getAvailableProducts();
        for (Product product : products) {
            if(product.getName().equals(productName)){
                return product;
            }
        }
        throw new ServiceException("Product does not exists.");

    }
}