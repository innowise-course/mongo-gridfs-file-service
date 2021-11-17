package ru.innowise.danko.mongogridfileservice.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    public final GridFsTemplate gridFsTemplate;

    public final GridFsOperations operations;

    @Autowired
    public FileServiceImpl(GridFsTemplate gridFsTemplate, GridFsOperations operations) {
        this.gridFsTemplate = gridFsTemplate;
        this.operations = operations;
    }

    @Override
    public String uploadFile(String name, MultipartFile multipartFile) {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "file");
        metaData.put("name", name);
        try {
            ObjectId id = gridFsTemplate.store(multipartFile.getInputStream(),
                    multipartFile.getName(), multipartFile.getContentType(), metaData);
            return id.toString();
        }
        catch (IOException e){
            /*aaaaaaa*/
        }
            return null;
    }

    @Override
    public FileEntity downloadFile(String id) {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        try {
            FileEntity fileEntity = FileEntity.builder()
                    .name(file.getMetadata().get("name").toString())
                    .stream(operations.getResource(file).getInputStream())
                    .build();
        } catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }
        return null;
    }
/*
    @Override
    public void deleteFile(String name) {

    }

    @Override
    public void deleteAllFiles() {

    }*/
}
