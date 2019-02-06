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
        try {
            createTables();
            // databaseConnector.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    private void createTables() throws DAOException {
        String productQuery = "CREATE TABLE IF NOT EXISTS Products("
                            + "id INTEGER PRIMARY KEY, "
                            + "name TEXT NOT NULL, "
                            + "price REAL NOT NULL, "
                            + "amount INTEGER DEFAULT 1, "
                            + "available TEXT DEFAULT 'true', "
                            + "category_id INTEGER NOT NULL, "
                            + "featured_category_id INTEGER NOT NULL)";

        String categoryQuery = "CREATE TABLE IF NOT EXISTS Category("
                            + "id INTEGER PRIMARY KEY, "
                            + "name TEXT NOT NULL, "
                            + "available TEXT DEFAULT 'true')";

        String featuredQuery = "CREATE TABLE IF NOT EXISTS FeaturedCategory("
                            + "id INTEGER PRIMARY KEY, "
                            + "name TEXT NOT NULL, "
                            + "available TEXT DEFAULT 'true', "
                            + "expiration_date DATE NOT NULL)";

        // String insertCategory = "INSERT INTO Category(name) VALUES('Fruits');"
        //                         + "INSERT INTO Category(name) VALUES('Diary');";
        // String insertProducts = "INSERT INTO Products(name) VALUES('Fruits')"

        try(Statement statement = databaseConnector.getConnection().createStatement()) {
            statement.executeUpdate(productQuery);
            statement.executeUpdate(categoryQuery);
            statement.executeUpdate(featuredQuery);
        } catch (SQLException e) {
            throw new DAOException("Cannot create tables");
        }
    }

    public boolean checkIfAvailable(String productName) throws DAOException {
        return true;
    }
    
    public List<Product> getAllProducts() throws DAOException{
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
            e.printStackTrace();
            throw new DAOException("message");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("message");
        }
    }

    public List<Product> getAvailableProducts() throws DAOException {
        List<Product> products = getAllProducts();
        List<Product> availableProducts = products.stream()
            .filter(p -> p.isAvailable())
            .collect(Collectors.toList());

        return availableProducts;
    }

    public Integer getCategoryIdByName(String name) throws DAOException {
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

    public Category getCategoryById(int id) throws DAOException {
        String productsQuery = "SELECT * FROM Category WHERE id = ?;";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery)) {
            
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            Category category = new Category(id,
                    results.getString("name"),
                    Boolean.getBoolean(results.getString("available")));

            return category;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Problem occured during querying category ID");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Problem occured during querying category ID");
        }   
    }

    public Product getProductById(int id) throws DAOException {
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

    public void updateCategoryName(String oldName, String newName) throws DAOException {
        String query = "UPDATE Category SET name = ? WHERE name = ?";
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

    public void addNewProduct(List<String> newProductData, int categoryId) throws DAOException {
        String query = "INSERT INTO Products(name, price, amount, available, category_id, featured_category_id) "
                        + "VALUES (?, ?, ?, ?, ?, 0)";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, newProductData.get(0));
            statement.setDouble(2, Double.valueOf(newProductData.get(1)));
            statement.setInt(3, Integer.valueOf(newProductData.get(2)));
            statement.setString(4, newProductData.get(3));
            statement.setInt(5, categoryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("message");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("message");
        }
    }

    public void addNewCategory(String name) throws DAOException {
        String query = "INSERT INTO Category(name) VALUES (?)";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void editProduct(int productID, List<String> edit, int categoryId) throws DAOException {
        String query = "UPDATE Products SET name = ?, price = ?, amount = ?, available = ?, category_id = ? WHERE id = ?";
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

    public void deactivateProduct(String name) throws DAOException {
        String query = "UPDATE Products SET available = 'false' WHERE name = ?";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void activateProduct(String name) throws DAOException {
        String query = "UPDATE Products SET available = 'true' WHERE name = ?";
        try (PreparedStatement statement = databaseConnector.c.prepareStatement(query)) {  

            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        }
    }

    public void addProductToDiscount(String discount, String productName) {
        
    }

    private List<Product> getProductsByCategory(int categoryId) throws DAOException {
        String productsQuery = "SELECT * FROM Category JOIN Products ON Category.id = Products.category_id "
                                + "WHERE Category.id = ?";

        try (PreparedStatement statement = databaseConnector.c.prepareStatement(productsQuery)) {
                
            statement.setInt(1, categoryId);
            ResultSet results = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while(results.next()){
                Product product = createProduct(results);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Problem occured during querying category ID");
        } catch (Exception e) {
            e.printStackTrace();
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
