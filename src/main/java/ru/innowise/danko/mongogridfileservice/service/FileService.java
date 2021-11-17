package ru.innowise.danko.mongogridfileservice.service;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;

import java.io.FileOutputStream;
import java.io.InputStream;

public interface FileService {

    String uploadFile(String name, MultipartFile multipartFile);

    FileEntity downloadFile(String id);
/*
    void deleteFile(String name);

    void deleteAllFiles();*/
}
