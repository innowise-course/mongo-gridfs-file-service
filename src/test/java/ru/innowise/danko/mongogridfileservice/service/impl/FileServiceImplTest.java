package ru.innowise.danko.mongogridfileservice.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.mock.web.MockMultipartFile;
import ru.innowise.danko.mongogridfileservice.dto.FileDto;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceImplTest {

    @Autowired
    public FileService fileService;

    @Autowired
    public GridFsTemplate gridFsTemplate;

    @Autowired
    public GridFsOperations operations;

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

    @AfterEach
    void deleteAllFiles(){
        gridFsTemplate.delete(new Query());
    }
}