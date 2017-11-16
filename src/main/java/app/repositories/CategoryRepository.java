package app.repositories;


import app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category AS c WHERE c.id = ?1")
    Category getCategoryById(Long categoryId);

}
