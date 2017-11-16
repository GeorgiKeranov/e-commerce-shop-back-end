package app.services.interfaces;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    final static String imagesPath = "/home/georgi/e-commerce-shop/images";

    // Saving image in our server directory(it is set in SpringBoot.class)
    // and returning the name of the image.
    String saveImage(MultipartFile file);

    void deleteImage(String imageName);
}
