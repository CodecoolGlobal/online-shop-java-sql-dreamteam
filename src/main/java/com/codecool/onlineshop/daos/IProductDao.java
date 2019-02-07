package com.codecool.onlineshop.daos;

import java.util.List;

import com.codecool.onlineshop.containers.Category;
import com.codecool.onlineshop.models.Product;

public interface IProductDao {
    List<Product> getAvailableProducts() throws DAOException;
    List<Product> getAllProducts() throws DAOException;
    boolean checkIfAvailable(String productName) throws DAOException;
    Integer getCategoryIdByName(String categoryName) throws DAOException;
    Category getCategoryById(int id) throws DAOException;
    Product getProductById(int id) throws DAOException;
    void updateCategoryName(String oldName, String newName) throws DAOException;
    void addNewCategory(String categoryName) throws DAOException;
    void addNewProduct(List<String> newProductData, int categoryId) throws DAOException;
    void editProduct(int productID, List<String> edit, int categoryId) throws DAOException;
    void deactivateProduct(String name) throws DAOException;
    void addProductToDiscount(String discount, String productName) throws DAOException;
}
