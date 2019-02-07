package com.codecool.onlineshop.daos;

import java.sql.Statement;

import com.codecool.onlineshop.models.User;
import com.codecool.onlineshop.models.Admin;
import com.codecool.onlineshop.models.Customer;
import java.util.List;
import com.codecool.onlineshop.containers.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;


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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
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
        } catch (SQLException  | ParseException e) {
            throw new DAOException("Something went wrong.");
        }
    }



    @Override
    public List<Order> getOrdersByUserName(String userName) throws DAOException{
        Statement stmt = null;
        List<Order> orders = new ArrayList<Order>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
        try {
            databaseConnector.connectToDatabase();
            databaseConnector.getConnection().setAutoCommit(false);
            stmt = databaseConnector.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("SELECT ORDERS.ID, STATUS, CREATED_AT, PAID_AT, USER_LOGIN, PASSWORD FROM ORDERS  LEFT JOIN USERS ON ORDERS.USER_LOGIN = USERS.LOGIN WHERE LOGIN = '" + userName + "'");
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
            System.out.println("lol");
            User user = new User(login, password);
            orders.add(new Order(id, null, user, created_at, paid_at, status));
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
    public void addOrder(String userLogin, String status, Date created_at) throws DAOException{
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


    




    


}
