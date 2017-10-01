package app.repositories;


import app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Product> getLast10Products();

}
