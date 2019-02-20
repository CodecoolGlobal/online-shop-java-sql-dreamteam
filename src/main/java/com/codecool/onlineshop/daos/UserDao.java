package com.codecool.onlineshop.daos;

import com.codecool.onlineshop.containers.Basket;
import com.codecool.onlineshop.containers.Order;
import com.codecool.onlineshop.models.Admin;
import com.codecool.onlineshop.models.Customer;
import com.codecool.onlineshop.models.Product;
import com.codecool.onlineshop.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class UserDao implements IUserDao {
    /*
    there are no baskets in database yet, so i used nulls to replace them in creating objects"
    */

    private DatabaseConnector databaseConnector;


    public UserDao() {
        this.databaseConnector = DatabaseConnector.getInstance();
        databaseConnector.connectToDatabase();
        createTables();
    }


    public void createTables(){
        Statement ordersStmt = null;
        Statement usersStmt = null;
        Statement productsStmt = null;
        try {
            databaseConnector.connectToDatabase();
            ordersStmt = databaseConnector.getConnection().createStatement();
            String ordersSql = "CREATE TABLE IF NOT EXISTS ORDERS " +
                    "(ID            INTEGER         PRIMARY KEY AUTOINCREMENT," +
                    " USER_LOGIN    TEXT             KEY NOT NULL, " +
                    " STATUS        TEXT                NOT NULL, " +
                    " CREATED_AT    TEXT                NOT NULL, " +
                    " PAID_AT       TEXT            )";
                  
            ordersStmt.executeUpdate(ordersSql);
            ordersStmt.close();

            usersStmt = databaseConnector.getConnection().createStatement();
            String usersSql = "CREATE TABLE IF NOT EXISTS USERS " +
                    "(ID            INTEGER         PRIMARY KEY AUTOINCREMENT," +
                    " LOGIN         TEXT         UNIQUE NOT NULL, " +
                    " PASSWORD      TEXT            NOT NULL, " +
                    " PERMISSION    CHAR(1)            NOT NULL)"; 
                  
            usersStmt.executeUpdate(usersSql);
            usersStmt.close();


            productsStmt = databaseConnector.getConnection().createStatement();
            String productsSql = "CREATE TABLE IF NOT EXISTS ORDERED_PRODUCTS " +
                    "(ID            INTEGER         PRIMARY KEY AUTOINCREMENT," +
                    " NAME_OF_PRODUCT         TEXT         NOT NULL, " +
                    " PRICE      REAL            NOT NULL, " +
                    " AMOUNT      INTEGER            NOT NULL, " +
                    " ORDER_ID     INTEGER            NOT NULL)";

            productsStmt.executeUpdate(productsSql);
            productsStmt.close();
            databaseConnector.getConnection().close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    @Override
    public User getUser(String login, String password) throws DAOException{
        Statement stmt = null;
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);

            stmt = databaseConnector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LOGIN, PASSWORD, PERMISSION FROM USERS WHERE LOGIN = '" + login + "' AND PASSWORD = '" + password +"'");
            String userLogin = rs.getString("LOGIN");
            String userPassword = rs.getString("PASSWORD");
            String userPermission = rs.getString("PERMISSION");
            rs.close();
            stmt.close();
            databaseConnector.getConnection().close();
            if (userPermission.equals("A")) {
                return new Admin(userLogin, userPassword);
            } else if (userPermission.equals("C")) {
                return new Customer(userLogin, userPassword);
            } else {
                return null;
            }
               
        } catch (SQLException e) {
            throw new DAOException("Wrong login or password. ");
        }
    }


    @Override
    public List<Order> getAllOrders() throws DAOException {
        List<Order> orders = new ArrayList<Order>();
        Statement stmt = null;
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ORDERS.ID, STATUS, CREATED_AT, PAID_AT, LOGIN, PASSWORD FROM ORDERS  LEFT JOIN USERS ON ORDERS.USER_LOGIN = USERS.LOGIN");

            while (rs.next()){
                Integer id = rs.getInt("ID");
                String login = rs.getString("login");
                String password = rs.getString("password");
                Date created_at = new Date(Long.valueOf(rs.getString("created_at")));
                Date paid_at = null;
                if (rs.getString("paid_at") != null) {
                    paid_at = new Date(Long.valueOf(rs.getString("paid_at")));
                }
                String status = rs.getString("status");
                User user = new User(login, password);
                Order order = new Order(id, null, user, created_at, paid_at, status);
                orders.add(order);
            }
            rs.close();
            stmt.close();
            databaseConnector.getConnection().close();
            return orders;
        } catch (SQLException e) {
            throw new DAOException("Something went wrong.");
        }
    }



    @Override
    public List<Order> getOrdersByUserName(String userName) throws DAOException{
        Statement stmt = null;
        Statement stmt2 = null;
        List<Order> orders = new ArrayList<Order>();
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("SELECT ORDERS.ID, STATUS, CREATED_AT, PAID_AT, USER_LOGIN, PASSWORD FROM " +
                    "ORDERS  LEFT JOIN USERS ON ORDERS.USER_LOGIN = USERS.LOGIN WHERE LOGIN = '" + userName + "'");
        while (rs.next()){
            Integer id = rs.getInt("ID");
            String login = rs.getString("user_login");
            String password = rs.getString("password");
            Date created_at = new Date(Long.valueOf(rs.getString("created_at")));
            Date paid_at = null;
            if (rs.getString("paid_at") != null) {
                paid_at = new Date(Long.valueOf(rs.getString("paid_at")));
            }
            String status = rs.getString("status");

            stmt2 = databaseConnector.getConnection().createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT ORDERS.ID, NAME_OF_PRODUCT, PRICE, AMOUNT FROM ORDERS  LEFT JOIN USERS ON ORDERS.USER_LOGIN = " +
                    "USERS.LOGIN LEFT JOIN ORDERED_PRODUCTS ON  ORDERS.ID = ORDERED_PRODUCTS.ORDER_ID  WHERE ORDERS.ID = '" + id + "'");

            List<Product> products = new ArrayList<>();
            while(rs2.next()) {
                String name = rs2.getString("NAME_OF_PRODUCT");
                double price = rs2.getDouble("PRICE");
                Integer amount = rs2.getInt("AMOUNT");
                products.add(new Product(name, price,amount ));
            }
            rs2.close();
            Basket basket = new Basket(products);

            User user = new User(login, password);
            orders.add(new Order(id, basket, user, created_at, paid_at, status));
            }
        rs.close();
        stmt.close();
        databaseConnector.getConnection().close();
        return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Wrong login or password");
        } 
    }

    
    @Override
    public void addOrder(String userLogin, String status, Date created_at, Basket basket) throws DAOException{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(created_at);
        Statement stmt = null;
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();
            String sql = "INSERT INTO ORDERS(USER_LOGIN, STATUS, CREATED_AT) "
                        + "VALUES ('" + userLogin + "', '" + status + "', '" + Long.toString(created_at.getTime()) + "');";
            stmt.executeUpdate(sql);
            databaseConnector.getConnection().commit();
            stmt.close();

            stmt = databaseConnector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM ORDERS");
            int maxId = rs.getInt(1);
            rs.close();
            stmt.close();

            List<Product> products = basket.getProducts();
            for (Product product: products) {
                stmt = databaseConnector.getConnection().createStatement();
                String name = product.getName();
                Integer amount = product.getAmount();
                Double price = product.getPrice();
                String sqlProduct = "INSERT INTO ORDERED_PRODUCTS(NAME_OF_PRODUCT, PRICE, AMOUNT, ORDER_ID)"
                        + "VALUES ('" + name + "' , '" + price + "', '" + amount + "', '" + maxId + "');";
                stmt.executeUpdate(sqlProduct);
                databaseConnector.getConnection().commit();
                stmt.close();
            }

            databaseConnector.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Wrong login or password");
        }
    }

    @Override
    public void addCustomer(String login, String password) throws DAOException{
        Statement stmt = null;
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();
            String sql = "INSERT INTO USERS(LOGIN, PASSWORD, PERMISSION) "
                        + "VALUES ('" + login + "', " + password + ", 'C'); ";
            stmt.executeUpdate(sql);
            databaseConnector.getConnection().commit();
            stmt.close();
            databaseConnector.getConnection().close();
        } catch (SQLException e) {
            throw new DAOException("Wrong login or password");
        }
    }


    @Override
    public void changeStatusToPaid(int orderId) throws DAOException {
        Statement stmt = null;
        Date paymentDate = new Date();
        try{
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();
            String sql ="UPDATE ORDERS set PAID_AT = + '" + Long.toString(paymentDate.getTime()) + "' " + ", STATUS = 'paid'" + " WHERE ID = '" + orderId + "';";
            stmt.executeUpdate(sql);
            databaseConnector.getConnection().commit();
            stmt.close();
            databaseConnector.getConnection().close();
        }
        catch(SQLException e){
           throw new DAOException("Wrong order ID! ");
        }
    }

    public void changeStatusesOfOrders() throws DAOException{

        Date dateOfNow = new Date();
        Long timeOfNow = dateOfNow.getTime();
        long timeFromPaymentToSend = 60000;
        long timeFromSendToDeliver = 120000;

        Statement stmt = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID, STATUS, CREATED_AT, PAID_AT FROM ORDERS");
            while(rs.next()){
                Date paid_at = null;
                if (rs.getString("paid_at") != null) {
                    int orderId = rs.getInt("ID");
                    paid_at = new Date(Long.valueOf(rs.getString("paid_at")));
                    Long timeOfPayment = paid_at.getTime();
                    if((timeOfNow-timeOfPayment)>timeFromPaymentToSend){
                        stmt2 = databaseConnector.getConnection().createStatement();
                        String sql ="UPDATE ORDERS set STATUS = 'on the way'" + " WHERE ID = '" + orderId + "';";
                        stmt2.executeUpdate(sql);
                        databaseConnector.getConnection().commit();
                        stmt2.close();
                    }
                    if((timeOfNow-timeOfPayment)>timeFromSendToDeliver){
                        stmt3 = databaseConnector.getConnection().createStatement();
                        String sql ="UPDATE ORDERS set STATUS = 'delivered'" + " WHERE ID = '" + orderId + "';";
                        stmt3.executeUpdate(sql);
                        databaseConnector.getConnection().commit();
                        stmt3.close();
                    }
                 }
            }
            rs.close();
            stmt.close();
            databaseConnector.getConnection().close();
        }
        catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public Set<Product> getDeliveredProductsByUserName(String userName) throws DAOException{
        Set<Product> deliveredProducts = new HashSet<>();
        Statement stmt = null;
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NAME_OF_PRODUCT, PRICE, AMOUNT FROM " +
                    "ORDERS  LEFT JOIN ORDERED_PRODUCTS ON ORDERS.ID = ORDERED_PRODUCTS.ORDER_ID WHERE STATUS ='delivered' " +
                    "AND" + " USER_LOGIN= '" + userName + "';");
            while (rs.next()){
                String name = rs.getString("NAME_OF_PRODUCT");
                Double price = rs.getDouble("PRICE");
                Integer amount = rs.getInt("AMOUNT");
                Product product = new Product(name, price, amount);
                deliveredProducts.add(product);
    }
            rs.close();
            stmt.close();
            databaseConnector.getConnection().close();
            return deliveredProducts;
        } catch (SQLException e) {
            throw new DAOException("Something went wrong.");
        }
    }




}

