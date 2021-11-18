package ru.innowise.danko.mongogridfileservice.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import java.io.IOException;

@SpringBootTest
public class ParameterizedTest {

    @Autowired
    public FileService fileService;

    @Autowired
    public GridFsTemplate gridFsTemplate;

    @Autowired
    public GridFsOperations operations;


    @org.junit.jupiter.params.ParameterizedTest
    @ValueSource(strings = {"619608c20189fa1558f13424"})
    void findFileById(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        FileEntity fileEntity = FileEntity.builder()
                .name(file.getMetadata().get("name").toString())
                .file(operations.getResource(file).getInputStream().readAllBytes())
                .build();
        System.out.println(fileEntity);
    }
}
