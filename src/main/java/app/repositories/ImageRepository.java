package app.repositories;

import app.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Image img WHERE img.name = ?1")
    void deleteImageByImageName(String imageName);

}
