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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    public GridFsTemplate gridFsTemplate;

    public GridFsOperations operations;

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
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "access error" ,e);
        }
    }

    @Override
    public FileEntity downloadFile(String id) {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        try {
            FileEntity fileEntity = FileEntity.builder()
                    .name(file.getMetadata().get("name").toString())
                    .type(operations.getResource(file).getContentType())
                    .file(operations.getResource(file).getInputStream().readAllBytes())
                    .build();
            return fileEntity;
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "file with id " + id + "does not exist", e);
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "io error", e);
        }
    }

}
