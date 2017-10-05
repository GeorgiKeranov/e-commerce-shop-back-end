package app.services.implementations;

import app.entities.Product;
import app.repositories.CategoryRepo;
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
    private CategoryRepo categoryRepository;

    @Override
    public String save(Product product) {

        Product productWithThatName = productRepository.findByName(product.getName());
        if(productWithThatName != null)
            return "Product with that name already exists.";

        productRepository.save(product);

        return null;
    }

    @Override
    public List<Product> getAllProductsByPage(int page) {

        if(page < 0) {
            return null;
        }

       return productRepository.getAllProductsPageable(new PageRequest(page, 10));
    }

    @Override
    public List<Product> getLastProducts() {

        return productRepository.getLast10Products();
    }


}
