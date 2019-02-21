package com.codecool.onlineshop.daos;

import com.codecool.onlineshop.containers.Category;
import com.codecool.onlineshop.containers.FeaturedCategory;
import com.codecool.onlineshop.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoStub extends ProductDao{

    List<Product> products = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    List<FeaturedCategory> featuredCategories = new ArrayList<>();

    public ProductDaoStub() {
        populateListsWithData();
    }

    private void populateListsWithData() {
        Category fruits = new Category(1, "fruits", true);
        Category diary = new Category(2, "diary", true);
        Category newcategory = new Category(3, "newcategory", true);
        Category vegetables = new Category(4, "vegetables", true);
        Category humans = new Category(5, "humans", true);
        categories.add(fruits);
        categories.add(diary);
        categories.add(newcategory);
        categories.add(vegetables);
        categories.add(humans);


        Product p1 = new Product(2, "apple", 3.0, 20, true, fruits, 0);
        Product p2 = new Product(3, "orange", 4.0, 10, true, fruits, 0);
        Product p3 = new Product(4, "milk", 2.0, 20, false, diary, 0);
        Product p4 = new Product(5, "toedit", 40.0, 10, true, fruits, 0);
        Product p5 = new Product(6, "banana", 3.0, 20, false, fruits, 0);
        Product p6 = new Product(7, "kamil", 1.0, 175, true, humans, 0);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
    }

    @Override
    public boolean checkIfAvailable(String productName) throws DAOException {
        return false;
    }

    @Override
    public boolean checkIfAvailableProduct(String productName) throws DAOException {
        productName.toLowerCase();
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Product> getAllProducts() throws DAOException {
        return products;
    }

    @Override
    public List<String> getAllCategoryNames() throws DAOException {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }

    @Override
    public List<Product> getAvailableProducts() throws DAOException {
        List<Product> availableProducts = products.stream()
                .filter(p -> p.isAvailable())
                .collect(Collectors.toList());

        return availableProducts;
    }

    @Override
    public Integer getCategoryIdByName(String name) throws DAOException {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category.getId();
            }
        }
        throw new DAOException("Cannot find category ID of such category name");
    }

    @Override
    public Category getCategoryById(int id) throws DAOException {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        throw new DAOException("Problem occured during querying category ID");
    }

    @Override
    public Product getProductById(int id) throws DAOException {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        throw new DAOException();
    }

    @Override
    public void updateCategoryName(String oldName, String newName) throws DAOException {
        for (Category category : categories) {
            if (category.getName().equals(oldName)) {
                category.setName(newName);
                break;
            }
        }
    }

    @Override
    public void addNewProduct(List<String> newProductData, int categoryId) throws DAOException {
        //can add duplicate
        products.add(new Product(products.get(products.size()-1).getId() + 1,
                newProductData.get(0),
                Double.valueOf(newProductData.get(1)),
                Integer.valueOf(newProductData.get(2)),
                Boolean.valueOf(newProductData.get(3)),
                getCategoryById(categoryId), 0));
    }

    @Override
    public void addNewCategory(String name) throws DAOException {
        //can add duplicate
        categories.add(new Category(categories.get(categories.size()-1).getId() + 1,
                name, true));
    }

    @Override
    public void editProduct(int productID, List<String> edit, int categoryId) throws DAOException {
        Product toEdit = null;
        for (Product product : products) {
            if (product.getId() == productID) {
                toEdit = product;
                break;
            }
        }

        if (toEdit != null) {
            toEdit.setName(edit.get(0));
            toEdit.setPrice(Double.valueOf(edit.get(1)));
            toEdit.setAmount(Integer.valueOf(edit.get(2)));
            toEdit.setAvailable(Boolean.valueOf(edit.get(3)));
            toEdit.setCategory(getCategoryById(categoryId));
        }
    }

    @Override
    public void deactivateProduct(String name) throws DAOException {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                product.setAvailable(false);
                break;
            }
        }
    }

    @Override
    public void activateProduct(String name) throws DAOException {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                product.setAvailable(true);
                break;
            }
        }
    }

    @Override
    public void addProductToDiscount(String discount, String productName) {
        //not yet implemented
    }
}
