package app.repositories;


import app.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product AS p ORDER BY id DESC")
    List<Product> getAllProductsPageable(Pageable pageable);

    Product findByName(String name);


}
