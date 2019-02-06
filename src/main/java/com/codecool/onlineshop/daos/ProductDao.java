package com.codecool.onlineshop.daos;

import java.sql.Connection;
import java.sql.DriverManager;
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
        databaseConnector.connectToDatabase(); // for testing
    }
    
    public List<Product> getAllProducts() {
        String productsQuery = "SELECT * FROM Products;";
        try (Statement statement = databaseConnector.c.createStatement();
            ResultSet results = statement.executeQuery(productsQuery)) {
            
            List<Product> products = new ArrayList<>();
            while(results.next()){
                Product product = createProduct(results);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
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
        String productsQuery = "SELECT * FROM Category WHERE name = ?;";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery)) {
            
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            Integer id = results.getInt("id");
            if (id.intValue() == 0) {
                throw new DAOException("Cannot find category ID of such category name");
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        }    
    }

    public Category getCategoryByID(int id) {
        String productsQuery = "SELECT * FROM Category WHERE id = ?;";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery)) {
            
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            List<Product> products = getProductsByCategory(id);
            results.next();
            Category category = new Category(id,
                    results.getString("name"),
                    Boolean.getBoolean(results.getString("available")),
                    products);

            return category;
            
        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        } catch (Exception e) {
            throw new DAOException("Problem occured during querying category ID");
        }   
    }

    public Product getProductById(int id) {
        String productsQuery = "SELECT * FROM Products WHERE id = ?;";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery)) {  
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            Product product = createProduct(results);
            return product;

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void updateCategoryName(String oldName, String newName) {
        String query = "UPDATE Category SET 'name' = ? WHERE 'name' = ?";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, newName);
            statement.setString(2, oldName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void addNewProduct(List<String> newProductData, int categoryId) {
        String query = "INSERT INTO Products('name', 'price', 'amount', 'available', category_id) "
                        + "VALUES ?, ?, ?, ?, ?";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, newProductData.get(0));
            statement.setDouble(2, Double.valueOf(newProductData.get(1)));
            statement.setInt(3, Integer.valueOf(newProductData.get(2)));
            statement.setString(4, newProductData.get(3));
            statement.setInt(5, categoryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void addNewCategory(String name) {
        String query = "INSERT INTO Category('name') VALUES ?";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, name);

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void editProduct(int productID, List<String> edit, int categoryId) {
        String query = "UPDATE Products SET 'name' = ?, 'price' = ?, 'amount' = ?, 'available' = ?, category_id = ? WHERE 'id' = ?";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, edit.get(0));
            statement.setDouble(2, Double.valueOf(edit.get(1)));
            statement.setInt(3, Integer.valueOf(edit.get(2)));
            statement.setString(4, edit.get(3));
            statement.setInt(5, categoryId);
            statement.setInt(6, productID);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    private List<Product> getProductsByCategory(int categoryId) {
        String productsQuery = "SELECT * FROM Category JOIN Product ON Category.id = Product.category_id "
                                + "WHERE Category.id = ?";

        try (PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery);
            ResultSet results = statement.executeQuery()) {
                
            statement.setInt(1, categoryId);
            List<Product> products = new ArrayList<>();
            while(results.next()){
                Product product = createProduct(results);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        } catch (Exception e) {
            throw new DAOException("Problem occured during querying category ID");
        }   
    }

    private Product createProduct(ResultSet results) throws Exception {
        Category category = getCategoryById(results.getInt("category_id"));
        Product product = new Product(results.getInt("id"),
                results.getString("name"),
                results.getDouble("price"),
                results.getInt("amount"),
                Boolean.valueOf(results.getString("available")),
                category);
        return product;
    }

}
