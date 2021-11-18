package ru.innowise.danko.mongogridfileservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;
import ru.innowise.danko.mongogridfileservice.service.FileService;

@RestController
@RequestMapping("/files/")
public class FileController {

    public FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> postFile(@RequestPart("file") MultipartFile file) {
        fileService.uploadFile(file.getName(), file);
        return ResponseEntity.ok().body("The file was successfully added ");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String id) {
        FileEntity file = fileService.downloadFile(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getName())
                .body(new ByteArrayResource(file.getFile()));
    }

}
