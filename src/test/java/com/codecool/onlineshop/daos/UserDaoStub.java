//package com.codecool.onlineshop.daos;
//
//
//import com.codecool.onlineshop.containers.Order;
//import com.codecool.onlineshop.models.Admin;
//import com.codecool.onlineshop.models.Customer;
//import com.codecool.onlineshop.models.User;
//
//import java.util.*;
//
//public class UserDaoStub extends UserDao {
//    Map<String, User> users = new HashMap<>();
//    List<Order> orders = new ArrayList<>();
//
//
//    public UserDaoStub() {
//        super();
//        populateDataBase();
//    }
//
//    @Override
//    public void createTables(){
//
//    }
//
//
//    public void populateDataBase() {
//        users.put("A", new User("kamil", "bed"));
//        users.put("C", new User("patryk", "ma"));
//        orders.add(new Order(3, null, new User(patryk, ma), new Date(Long.valueOf("1549569037241")), null));
//        orders.add(new Order(4, null, new User(patryk, ma), new Date(Long.valueOf("1549577935108")), null));
//    }
//
//
//
//
//    @Override
//    public User getUser(String login, String password) throws DAOException {
//        for (Map.Entry<String, User> entry : users.entrySet()) {
//            if (entry.getValue().getName() == login & entry.getValue().getPassword() == password) {
//                if (entry.getKey() == "A") {
//                    return new Admin(login, password);
//                } else if (entry.getKey() == "C") {
//                    return new Customer(login, password);
//                }
//            }
//        }
//        return null;
//    }
//
//
////    @Override
////    public List<Order> getAllOrders() throws DAOException {
////
////    }
//
//
//
////    @Override
////    public List<Order> getOrdersByUserName(String userName) throws DAOException{
////
////    }
//
//
//    @Override
//    public void addOrder(String userLogin, String status, Date created_at) throws DAOException{
//
//    }
//
//    @Override
//    public void addCustomer(String login, String password) throws DAOException{
//
//    }
//
//
//}
//
//
//
