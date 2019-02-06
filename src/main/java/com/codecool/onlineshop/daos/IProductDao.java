package com.codecool.onlineshop.daos;

import java.util.List;

import com.codecool.onlineshop.containers.Category;
import com.codecool.onlineshop.models.Product;

public interface IProductDao {
    List<Product> getAvailableProducts();
    List<Product> getAllProducts();
    boolean checkIfAvailable(String productName);
    Integer getCategoryIdByName(String categoryName);
    Category getCategoryById(int id);
    Product getProductById(int id);
    void updateCategoryName(String oldName, String newName);
    void addNewCategory(String categoryName);
    void addNewProduct(List<String> newProductData);
    void editProduct(Integer productId, List<String> editData);
    void deactivateProduct(String name);
    void addProduct(String discount, String productName);
}
