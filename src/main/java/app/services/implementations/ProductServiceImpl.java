package app.services.implementations;

import app.entities.Category;
import app.entities.Product;
import app.repositories.CategoryRepo;
import app.repositories.ProductRepository;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public String save(Product product) {

        productRepository.save(product);

        return null;
    }

    @Override
    public List<Product> getLastProducts() {

        return productRepository.getLast10Products();
    }


}
