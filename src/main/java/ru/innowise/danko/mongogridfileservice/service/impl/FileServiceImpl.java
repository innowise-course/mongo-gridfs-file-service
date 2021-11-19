package ru.innowise.danko.mongogridfileservice.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.dto.FileDto;
import ru.innowise.danko.mongogridfileservice.service.FileService;
import ru.innowise.danko.mongogridfileservice.util.exceptions.FileNotFound;

import java.io.*;

import static java.util.Optional.ofNullable;

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
    public String uploadFile(MultipartFile multipartFile) {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "file");
        try {
            return gridFsTemplate.store(multipartFile.getInputStream(),
                    multipartFile.getName(), multipartFile.getContentType(),metaData).toString();
        } catch (IOException e) {
            throw new IllegalStateException("illegal state, " + e.getMessage());
        }
    }

    @Override
    public FileDto downloadFile(String id) {
        return ofNullable(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))))
                .map(item -> FileDto.builder()
                        .name(item.getFilename())
                        .type(operations.getResource(item).getContentType())
                        .file(extract(item))
                        .build())
                .orElseThrow(() -> new FileNotFound(id));
    }

    private byte[] extract(GridFSFile file) {
        try {
            return operations.getResource(file).getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new IllegalStateException("illegal state, " + e.getMessage());
        }
    }

}
