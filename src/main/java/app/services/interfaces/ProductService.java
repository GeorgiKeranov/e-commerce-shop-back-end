package app.services.interfaces;


import app.entities.Category;
import app.entities.Product;

import java.util.List;

public interface ProductService {

    // return error message if there is error.
    // return null if there is not error.
    String save(Product product);

    String update(Product product);

    void saveCategory(Category category);

    void updateCategory(Category category);

    // Getting all products by page.
    List<Product> getAllProductsByPage(int page);

    List<Category> getAllCategories();

    Product getProductById(Long id);

    Category getCategoryById(Long categoryId);

    void setMainImageNameByProductId(String imageName, Long productId);
}
