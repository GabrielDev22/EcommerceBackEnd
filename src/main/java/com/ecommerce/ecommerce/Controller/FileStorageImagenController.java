package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Service.FileStorageImagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileStorageImagenController {

    @Value("${file.upload-dir}")
    private String storageImages;

    @Autowired
    private FileStorageImagenesService fileStorageImagenesService;

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImagenesFilesStorage(@PathVariable String imageName) throws IOException {
        Path pathUrl = Paths.get(storageImages, imageName);

        if(!Files.exists(pathUrl)){
            return ResponseEntity.notFound().build();
        }

        byte[] imagenBytes = Files.readAllBytes(pathUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imagenBytes.length);

        return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
    }

}
