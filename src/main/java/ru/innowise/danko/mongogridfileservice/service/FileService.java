package ru.innowise.danko.mongogridfileservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;

public interface FileService {

    String uploadFile(String name, MultipartFile multipartFile);

    FileEntity downloadFile(String id);

}
