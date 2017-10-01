package app.services.interfaces;


import app.entities.Product;

public interface ProductService {

    // return error message if there is error.
    // return null if there is not error.
    String save(Product product);

}
