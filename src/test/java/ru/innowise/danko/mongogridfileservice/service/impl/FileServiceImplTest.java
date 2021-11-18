package ru.innowise.danko.mongogridfileservice.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.mock.web.MockMultipartFile;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import java.io.IOException;

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
    void uploadFile() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("data","filename.txt","text/pain","some xml".getBytes());
        ObjectId id = gridFsTemplate.store(multipartFile.getInputStream(),
                multipartFile.getName(), multipartFile.getContentType());
        assertNotNull(id);
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id.toString())));
    }

    @Test
    void downloadFile() throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is("619608c20189fa1558f13424")));
        FileEntity fileEntity = FileEntity.builder()
                    .name(file.getMetadata().get("name").toString())
                    .type(operations.getResource(file).getContentType())
                    .file(operations.getResource(file).getInputStream().readAllBytes())
                    .build();
        assertNotNull(new ByteArrayResource(fileEntity.getFile()));
    }

}