package ru.innowise.danko.mongogridfileservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.dto.FileDto;

public interface FileService {

    String uploadFile(MultipartFile multipartFile);

    FileDto downloadFile(String id);

}
