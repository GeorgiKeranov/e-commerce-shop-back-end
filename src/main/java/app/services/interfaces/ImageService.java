package app.services.interfaces;


public interface ImageService {

    void saveImageWithNameAndProductId(String imageName, Long productId);

    void deleteImageByImageName(String imageName);

}
