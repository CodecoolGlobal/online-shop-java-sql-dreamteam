package com.codecool.onlineshop.daos;

import com.codecool.onlineshop.containers.Basket;
import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.models.Admin;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoStub extends UserDao {

    List<User> users = new ArrayList<>();;
    List<Order> orders = new ArrayList<>();;

    public UserDaoStub() {
        populateListsWithData();
    }

    private void populateListsWithData() {
        users.add(new Customer("patryk", "ma"));
        users.add(new Admin("kamil", "bed"));

        orders.add(new Order(3, null, users.get(0), new Date(Long.valueOf("1549569037241")), null, "submitted"));
        orders.add(new Order(4, null, users.get(0), new Date(Long.valueOf("1549577935108")), null, "submitted"));
    }

    @Override
    public User getUser(String login, String password) throws DAOException {
        if (login.equals("kamil") && password.equals("bed")) {
            return (Admin) users.get(1);
        }
        if (login.equals("patryk") && password.equals("ma")) {
            return (Customer) users.get(0);
        }
        throw new DAOException("Wrong login or password");
    }

    @Override
    public List<Order> getAllOrders() throws DAOException {
        return orders;
    }

    @Override
    public List<Order> getOrdersByUserName(String userName) throws DAOException {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUser().getName().equals(userName)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public void addOrder(String userLogin, String status, Date created_at, Basket basket) throws DAOException {
        User currentUser = null;
        for (User user : users) {
            if (user.getName().equals(userLogin)) {
                currentUser = user;
            }
        }
        orders.add(new Order(orders.get(orders.size()-1).getId() + 1, null, currentUser, created_at, null, status));
    }

    @Override
    public void addCustomer(String login, String password) throws DAOException {
        //not used yet
    }
}
