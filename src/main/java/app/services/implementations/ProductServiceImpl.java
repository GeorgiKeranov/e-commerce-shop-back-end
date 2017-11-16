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
    public void updateCategory(Category category) {

        // Getting the old category by id.
        Category oldCategory = categoryRepository.getOne(category.getId());
        if(oldCategory != null) {
            // Setting the new category name on the old category object.
            oldCategory.setCategoryName(category.getCategoryName());
        }

        categoryRepository.save(oldCategory);
    }

    @Override
    public List<Product> getAllProductsByPage(int page) {

        if(page < 0) {
            return null;
        }

       return productRepository.getAllProductsPageable(new PageRequest(page, 10));
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
    public void setMainImageNameByProductId(String imageName, Long productId) {
        productRepository.setMainImageNameByProductId(imageName, productId);
    }

}
