package app.services.implementations;


import app.entities.Image;
import app.entities.Product;
import app.repositories.ImageRepository;
import app.services.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Long saveImageWithNameAndProductId(String imageName, Long productId) {

        Image image = new Image();
        image.setName(imageName);

        Product product = new Product();
        product.setId(productId);

        image.setProduct(product);
        imageRepository.save(image);

        return image.getId();
    }

}
