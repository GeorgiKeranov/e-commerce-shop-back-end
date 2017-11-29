package app.services.implementations;

import app.entities.Category;
import app.entities.Product;
import app.repositories.CategoryRepository;
import app.repositories.ProductRepository;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public String save(Product product) {

        Product productWithThatTitle = productRepository.findByTitle(product.getTitle());
        if(productWithThatTitle != null)
            return "Product with that title already exists.";

        productRepository.save(product);

        return null;
    }

    @Override
    public String update(Product product) {

        Product oldProd = productRepository.getProductById(product.getId());

        // Checking if the product exists.
        if(oldProd != null) {

            // Check if the new product imageName is empty.
            if(product.getMainImageName() == null) {
                // Getting the old mainImageName and setting it to the new product.
                product.setMainImageName(oldProd.getMainImageName());
            }

            productRepository.save(product);
            return null;
        }

        else
            return "Product doesn\'t exist";
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Product> getAllProductsByPage(int page) {

        if(page < 0) {
            return null;
        }

       return productRepository.getAllProductsPageable(new PageRequest(page, 12));
    }

    @Override
    public List<Product> getProductsByCategoryIdAndPage(Long categoryId, int page) {
        return productRepository.getProductsByCategoryIdAndPage(categoryId, new PageRequest(page, 12));
    }

    @Override
    public List<Product> getProductsByWordAndPage(String word, int page) {
        return productRepository.getProductsByWordAndPage(word, new PageRequest(page, 12));
    }

    @Override
    public List<Product> getProductsByCategoryIdAndWordAndPage(Long categoryId, String word, int page) {
        return productRepository.getProductsByCategoryIdAndWordAndPage(
                categoryId, word, new PageRequest(page, 12)
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    @Override
    public Long getCountOfPagesForAllProducts() {
        // Getting count of products and divide it to 12 ( our count of products for 1 page)
        // To get the count of maximum pages.
        Long countOfProducts = productRepository.getCountOfPagesForAllProducts();
        Long countOfPages = countOfProducts / 12;

        // Adding one more page if they are more products
        if(countOfProducts % 12 != 0) {
            countOfPages++;
        }

        return countOfPages;
    }

    @Override
    public Long getCountOfPagesForProductsByCategoryId(Long categoryId) {
        // Getting count of products and divide it to 12 ( our count of products for 1 page)
        // To get the count of maximum pages.
        Long countOfProducts = productRepository.getCountOfPagesForProductsByCategoryId(categoryId);
        Long countOfPages = countOfProducts / 12;

        // Adding one more page if they are more products
        if(countOfProducts % 12 != 0) {
            countOfPages++;
        }

        return countOfPages;
    }

    @Override
    public Long getCountOfPagesForProductsByWord(String searchWord) {
        // Getting count of products and divide it to 12 ( our count of products for 1 page)
        // To get the count of maximum pages.
        Long countOfProducts = productRepository.getCountOfPagesForProductsByWord(searchWord);
        Long countOfPages = countOfProducts / 12;

        // Adding one more page if they are more products
        if(countOfProducts % 12 != 0) {
            countOfPages++;
        }

        return countOfPages;
    }

    @Override
    public Long getCountOfPagesForProductsByCategoryIdAndWord(Long categoryId, String searchWord) {
        // Getting count of products and divide it to 12 ( our count of products for 1 page)
        // To get the count of maximum pages.
        Long countOfProducts = productRepository.getCountOfPagesForProductsByCategoryIdAndWord(categoryId, searchWord);
        Long countOfPages = countOfProducts / 12;

        // Adding one more page if they are more products
        if(countOfProducts % 12 != 0) {
            countOfPages++;
        }

        return countOfPages;
    }

    @Override
    public void setMainImageNameByProductId(String imageName, Long productId) {
        productRepository.setMainImageNameByProductId(imageName, productId);
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.delete(productId);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.delete(categoryId);
    }

}
