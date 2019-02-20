package com.codecool.onlineshop.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.codecool.onlineshop.containers.Category;
import com.codecool.onlineshop.models.Product;

public class ProductDao implements IProductDao {

    DatabaseConnector databaseConnector;

    public ProductDao() {
        databaseConnector = DatabaseConnector.getInstance();
        try {
            createTables();
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

        try {
            databaseConnector.connectToDatabase();
            Statement statement = databaseConnector.getConnection().createStatement();
            statement.executeUpdate(productQuery);
            statement.executeUpdate(categoryQuery);
            statement.executeUpdate(featuredQuery);
            databaseConnector.c.close();
        } catch (SQLException e) {
            throw new DAOException("Cannot create tables");
        }
    }
    
    public boolean checkIfAvailable(String productName) throws DAOException {
        return false;
    }

    public boolean checkIfAvailableProduct(String productName) throws DAOException{
        productName.toLowerCase();
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if(product.getName().equals(productName)){
                return false;
            }
        }
        return true;
    }

    
    public List<Product> getAllProducts() throws DAOException{
        String productsQuery = "SELECT * FROM Products;";
        Statement statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.createStatement();
            results = statement.executeQuery(productsQuery);
            
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
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public List<String> getAllCategoryNames() throws DAOException{
        String productsQuery = "SELECT name FROM Category;";
        Statement statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.createStatement();
            results = statement.executeQuery(productsQuery);
            
            List<String> categoryNames = new ArrayList<>();
            while(results.next()){
                String name = results.getString("name");
                categoryNames.add((name));
            }
            return categoryNames;

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public List<String> getAllFeaturedCategoryNames() throws DAOException{
        String productsQuery = "SELECT name FROM FeaturedCategory;";
        Statement statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.createStatement();
            results = statement.executeQuery(productsQuery);

            List<String> categoryNames = new ArrayList<>();
            while(results.next()){
                String name = results.getString("name");
                categoryNames.add((name));
            }
            return categoryNames;

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }

    }


    public List<Product> getAvailableProducts() throws DAOException {
        List<Product> products = getAllProducts();
        List<Product> availableProducts = products.stream()
            .filter(p -> p.isAvailable())
            .collect(Collectors.toList());

        return availableProducts;
    }

    public Category getCategoryById(int id) throws DAOException {
        String productsQuery = "SELECT * FROM Category WHERE id = ?;";
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setInt(1, id);
            results = statement.executeQuery();
            results.next();
            Category category = new Category(id,
                    results.getString("name"),
                    Boolean.getBoolean(results.getString("available")));

            return category;

        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        } catch (Exception e) {
            throw new DAOException("Problem occured during querying category ID");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public Category getFeaturedCategoryById(int id) throws DAOException {
        String productsQuery = "SELECT * FROM FeaturedCategory WHERE id = ?;";
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setInt(1, id);
            results = statement.executeQuery();
            results.next();
            Category category = new Category(id,
                    results.getString("name"),
                    Boolean.getBoolean(results.getString("available")));

            return category;

        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        } catch (Exception e) {
            throw new DAOException("Problem occured during querying category ID");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public Integer getFeaturedCategoryIdByName(String name) throws DAOException {
        String productsQuery = "SELECT * FROM FeaturedCategory WHERE name = ?;";
        PreparedStatement  statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setString(1, name);
            results = statement.executeQuery();
            results.next();
            Integer id = results.getInt("id");
            if (id.intValue() == 0) {
                throw new DAOException("Cannot find category ID of such category name");
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public Integer getCategoryIdByName(String name) throws DAOException {
        String productsQuery = "SELECT * FROM Category WHERE name = ?;";
        PreparedStatement  statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setString(1, name);
            results = statement.executeQuery();
            results.next();
            Integer id = results.getInt("id");
            if (id.intValue() == 0) {
                throw new DAOException("Cannot find category ID of such category name");
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Problem occured during querying category ID");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }
    public Product getProductByName(String name) throws DAOException {
        String productsQuery = "SELECT * FROM Products WHERE name = ?;";
        PreparedStatement  statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setString(1, name);
            results = statement.executeQuery();
            results.next();
            Product product = createProduct(results);
            return product;

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public Product getProductById(int id) throws DAOException {
        String productsQuery = "SELECT * FROM Products WHERE id = ?;";
        PreparedStatement  statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);
            statement.setInt(1, id);
            results = statement.executeQuery();
            results.next();
            Product product = createProduct(results);
            return product;

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void updateCategoryName(String oldName, String newName) throws DAOException {
        String query = "UPDATE Category SET name = ? WHERE name = ?";
        PreparedStatement statement = null;
        try {  
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void updateProductName(String oldName, String newName) throws DAOException {
        String query = "UPDATE Products SET name = ? WHERE name = ?";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void updateProductPrice(String oldName, double newPrice) throws DAOException {
        String query = "UPDATE Products SET price = ? WHERE name = ?";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setDouble(1, newPrice);
            statement.setString(2, oldName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

   /* public boolean isProductQutOfStock(Product product){
        String query = "SELECT name FROM"

    }*/

    public void updateProductQuantity(String oldName, int newQuantity) throws DAOException {
        String query = "UPDATE Products SET amount = ? WHERE name = ?";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setInt(1, newQuantity);
            statement.setString(2, oldName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void addNewProduct(List<String> newProductData, int categoryId) throws DAOException {
        String query = "INSERT INTO Products(name, price, amount, available, category_id, featured_category_id) "
                        + "VALUES (?, ?, ?, ?, ?, 0)";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
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
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void addNewCategory(String name) throws DAOException {
        String query = "INSERT INTO Category(name) VALUES (?)";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void addNewFeaturedCategory(String name, Date expirationDate) throws DAOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(expirationDate);
        String query = "INSERT INTO FeaturedCategory(name, expiration_date) VALUES (?, ?)";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, strDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void editProduct(int productID, List<String> edit, int categoryId) throws DAOException {
        String query = "UPDATE Products SET name = ?, price = ?, amount = ?, available = ?, category_id = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {  
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
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
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    public void deactivateProduct(String name) throws DAOException {
        String query = "UPDATE Products SET available = 'false' WHERE name = ?";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } try {
            if (statement != null) {
                statement.close();
            }
            databaseConnector.getConnection().close();
        } catch (SQLException e) {

        }
    }

    public void activateProduct(String name) throws DAOException {
        String query = "UPDATE Products SET available = 'true' WHERE name = ?";
        PreparedStatement statement = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } try {
            if (statement != null) {
                statement.close();
            }
            databaseConnector.getConnection().close();
        } catch (SQLException e) {

        }
    }

    public void addProductToDiscount(String discount, String productName) {
        String productsQuery = "";
        PreparedStatement statement = null;
        
    }

    public void featureProduct(int categoryId, String productName) throws DAOException {
        //TODO
        // dodaj produkt do kategorii(zmienic featured category na category ID)
        String query = "UPDATE Products SET featured_category = '?' WHERE name = ?";
        PreparedStatement statement = null;
        ResultSet results = null;
        try{
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(query);
            statement.setInt(1, categoryId);
            statement.setString(2, productName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("message");
        } catch (Exception e) {
            throw new DAOException("message");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Product> getProductsByCategory(int categoryId) throws DAOException {
        String productsQuery = "SELECT * FROM Category JOIN Products ON Category.id = Products.category_id "
                                + "WHERE Category.id = ?";
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            databaseConnector.connectToDatabase();
            statement = databaseConnector.c.prepareStatement(productsQuery);   
            statement.setInt(1, categoryId);
            results = statement.executeQuery();
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
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                databaseConnector.getConnection().close();
            } catch (SQLException e) {

            }
        }
    }

    private Product createProduct(ResultSet results) throws Exception {
        //TODO check usage
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
