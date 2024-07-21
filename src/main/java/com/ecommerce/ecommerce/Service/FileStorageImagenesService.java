package com.ecommerce.ecommerce.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageImagenesService {

    @Value("${file.upload-dir}")
    private String storageImages;

    public String storeFile(MultipartFile file){
        try{
            Path uploadPath = Paths.get(storageImages);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);
            return fileName;

        }catch (IOException e){
            throw new RuntimeException("Error storing file", e);
        }
    }

}
