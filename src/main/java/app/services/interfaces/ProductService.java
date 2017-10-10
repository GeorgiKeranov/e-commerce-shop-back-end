package app.services.interfaces;


import app.entities.Product;

import java.util.List;

public interface ProductService {

    // return error message if there is error.
    // return null if there is not error.
    String save(Product product);

    // Getting all products by page.
    List<Product> getAllProductsByPage(int page);

    void setMainImageIdByImageIdAndProductId(Long imageId, Long productId);

}
