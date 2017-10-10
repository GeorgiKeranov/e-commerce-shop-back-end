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

            // Creating unique name for the image.
            String tempName = nameOfTheImage;
            int randomNumber = 1;
            while(Files.exists(location.resolve(tempName))) {
                tempName = nameOfTheImage;
                tempName = tempName + randomNumber;
                randomNumber++;
            }

            nameOfTheImage = tempName;

            Files.copy(file.getInputStream(), location.resolve(nameOfTheImage));
            return nameOfTheImage;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
