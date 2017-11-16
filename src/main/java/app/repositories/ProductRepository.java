package app.repositories;


import app.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product AS p ORDER BY id DESC")
    List<Product> getAllProductsPageable(Pageable pageable);

    Product findByTitle(String title);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.mainImageName = ?1 where p.id = ?2")
    void setMainImageNameByProductId(String imageName, Long productId);

    @Query("SELECT p FROM Product p WHERE p.id = ?1")
    Product getProductById(Long id);
}
