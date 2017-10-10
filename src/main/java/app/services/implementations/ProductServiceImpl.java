package app.services.implementations;

import app.entities.Product;
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

    @Override
    public String save(Product product) {

        Product productWithThatTitle = productRepository.findByTitle(product.getTitle());
        if(productWithThatTitle != null)
            return "Product with that title already exists.";

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
    public void setMainImageIdByImageIdAndProductId(Long imageId, Long productId) {
        productRepository.setMainImageIdByImageIdAndProductId(imageId, productId);
    }

}
