package ru.innowise.danko.mongogridfileservice.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.innowise.danko.mongogridfileservice.dto.FileDto;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
public class FileServiceImplTestContainerTest extends AbstractIntegrationTest {

    @Autowired
    public FileService fileService;

    @Test
    void uploadFile(){
        MockMultipartFile multipartFile = new MockMultipartFile("data","filename.txt","text/pain","some xml".getBytes());
        String id = fileService.uploadFile(multipartFile);
        assertNotNull(id);
    }

    @Test
    void downloadFile(){
        MockMultipartFile before = new MockMultipartFile("data","filename.txt","text/pain","some xml".getBytes());
        String id = fileService.uploadFile(before);
        FileDto after = fileService.downloadFile(id);
        assertEquals(before.getName(),after.getName());
    }

}
