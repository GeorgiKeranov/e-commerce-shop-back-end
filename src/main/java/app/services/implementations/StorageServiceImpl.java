package app.services.implementations;

import app.services.interfaces.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public String saveImage(MultipartFile file) {

        try {
            Path location = Paths.get(imagesPath);

            String nameOfTheImage = file.getOriginalFilename();
            int positionOfLastDot = nameOfTheImage.lastIndexOf(".");
            String extensionOfTheImage = nameOfTheImage.substring(positionOfLastDot, nameOfTheImage.length());
            nameOfTheImage = nameOfTheImage.substring(0, positionOfLastDot);

            // Creating unique name for the image.
            String tempName = nameOfTheImage;
            int randomNumber = 1;
            while(Files.exists(location.resolve(tempName + extensionOfTheImage))) {
                tempName = nameOfTheImage;
                tempName = tempName + randomNumber;
                randomNumber++;
            }

            nameOfTheImage = tempName + extensionOfTheImage;

            Files.copy(file.getInputStream(), location.resolve(nameOfTheImage));
            return nameOfTheImage;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteImage(String imageName) {

        Path folderPath = Paths.get(imagesPath);
        Path filePath = folderPath.resolve(imageName);

        try {

            if(Files.exists(filePath))
                Files.delete(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
