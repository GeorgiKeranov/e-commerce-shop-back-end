package app.services.interfaces;


import app.entities.Category;
import app.entities.Product;

import java.util.List;

public interface ProductService {

    // return error message if there is error.
    // return null if there is not error.
    String save(Product product);
    void saveCategory(Category category);

    String update(Product product);
    void updateCategory(Category category);
    void setMainImageNameByProductId(String imageName, Long productId);

    void deleteProductById(Long productId);
    void deleteCategoryById(Long categoryId);

    // Getting all products by page.
    List<Product> getAllProductsByPage(int page);
    List<Product> getProductsByCategoryIdAndPage(Long categoryId, int page);
    List<Product> getProductsByWordAndPage(String word, int page);
    List<Product> getProductsByCategoryIdAndWordAndPage(Long categoryId, String word, int page);
    List<Category> getAllCategories();
    Product getProductById(Long id);
    Category getCategoryById(Long categoryId);

    Long getCountOfPagesForAllProducts();
    Long getCountOfPagesForProductsByCategoryId(Long categoryId);
    Long getCountOfPagesForProductsByWord(String searchWord);
    Long getCountOfPagesForProductsByCategoryIdAndWord(Long categoryId, String searchWord);
}
