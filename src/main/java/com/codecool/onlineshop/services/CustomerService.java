package com.codecool.onlineshop.services;

import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.daos.DAOException;
import com.codecool.onlineshop.daos.ProductDao;
import com.codecool.onlineshop.daos.UserDao;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerService {
    private Customer customer;
    private ProductDao productDao;
    private UserDao userDao;

    public CustomerService(Customer customer, ProductDao productDao, UserDao userDao) {
        this.customer = customer;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    public Customer getCustomer() {
        return this.customer;
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
        Product productToAdd = new Product(product.getId(), product.getName(), product.getPrice(), amount, product.isAvailable(), product.getCategory(), product.getRate());
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

    public List<Product> getAllProducts() throws ServiceException {
        try {
            return productDao.getAvailableProducts();
        } catch (DAOException e) {
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

    public void placeAnOrder() throws DAOException, ServiceException {
        if (customer.getBasket().getProducts().isEmpty()) {
            throw new ServiceException("You cant place an empty order!");
        }
        userDao.addOrder(getCustomerName(), "submited", new Date(), getCustomer().getBasket());
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

    public List<Order> getCustomerOrders() throws DAOException {
        List<Order> orders = userDao.getOrdersByUserName(customer.getName());
        return orders;
    }


    public List<Order> getUnratedAndDeliveredOrders() throws DAOException{
        List<Order> orders = userDao.getUnratedAndDeliveredOrdersByUserName(customer.getName());
        return orders;
    }

    public List<Product> getProductByCategory(String categoryName) throws DAOException {
        List<Product> products = productDao.getAvailableProducts();
        int categoryId = productDao.getCategoryIdByName(categoryName);
        products = products.stream().filter(p -> p.getCategory().getId() == categoryId).collect(Collectors.toList());
        return products;
    }

    public Product getProductByName(String productName) throws DAOException, ServiceException {
        List<Product> products = productDao.getAvailableProducts();
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        throw new ServiceException("Product does not exists.");

    }

    public void payForOrder(int orderId) throws DAOException, ServiceException {
        List<Order> orders = getCustomerOrders();
        for (Order order : orders) {
            if (order.getId() == orderId && order.getStatus().equals("submited")) {
                userDao.changeStatusToPaid(orderId);
                return;
            }
        }
        throw new ServiceException("There is no such order Id or you already paid for it.");

    }

    public void changeStatuses() throws DAOException {
        userDao.changeStatusesOfOrders();
    }

    public Set<Product> getDeliveredProducts() throws DAOException {
        Set<Product> products = userDao.getDeliveredProductsByUserName(customer.getName());
        return products;
    }

    public void updateProductsRatings(Map<String, Integer> rates) throws DAOException {
        productDao.updateRatings(rates);
    }

    public void setOrdersAsRated() {
        try {
            List<Order> orders = userDao.getOrdersByUserName(getCustomerName());
            userDao.setOrdersAsRated(getCustomerName());
        } catch (DAOException e) {

        }
    }

}