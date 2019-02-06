package com.codecool.onlineshop.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codecool.onlineshop.containers.Category;
import com.codecool.onlineshop.models.Product;

public class ProductDao implements IProductDao {

    DatabaseConnector databaseConnector;

    public ProductDao() {
        databaseConnector = DatabaseConnector.getInstance();
    }
    
    public List<Product> getAllProducts() {
        try{
            String productsQuery = "SELECT * FROM Products;";
            Statement statement = databaseConnector.c.createStatement();
    
            ResultSet results = statement.executeQuery(productsQuery);
    
            List<Product> products = new ArrayList<>();
            while(results.next()){
                Category category = getCategoryById(results.getInt("category_id"));
                Product product = new Product(results.getInt("id"),
                        results.getString("name"),
                        results.getDouble("price"),
                        results.getInt("amount"),
                        Boolean.valueOf(results.getString("available")),
                        category);
            }

            return products;
        } catch (SQLException e) {
            
        }    
    }

    public List<Product> getAvailableProducts() {
        List<Product> products = getAllProducts();
        List<Product> availableProducts = products.stream()
            .filter(p -> p.isAvailable())
            .collect(Collectors.toList());

        return availableProducts;
    }

    public Integer getCategoryIdByName(String name) {
        try{
            String productsQuery = "SELECT * FROM Category WHERE name = ?;";
            PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setString(1, name);
    
            ResultSet results = statement.executeQuery();
            results.next();
            Integer id = results.getInt("id");
            if (id.intValue() == 0) {
                throw new DAOException("Cannot find category ID of such category name");
            }
        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        }    
    }

}
