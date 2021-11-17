package ru.innowise.danko.mongogridfileservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceImplTest {

    @Autowired
    public FileService fileService;


    @Test
    void uploadFile() {
        Path path = Paths.get("/home/ppprka/snap/postman/149/Postman/files/drop.avi");
        String name = "drop.avi";
        String originalFileName = "drop.avi";
        String contentType = "video/*";
        byte[] content = null;
        try{
            content = Files.readAllBytes(path);
        }
        catch (IOException e){

        }
        MultipartFile file = new MockMultipartFile(name,originalFileName,contentType,content);
        String drop = fileService.uploadFile("drop", file);
        assertNotNull(drop);
    }

    @Test
    void downloadFile() {
    }
}