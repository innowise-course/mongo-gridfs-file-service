package ru.innowise.danko.mongogridfileservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.innowise.danko.mongogridfileservice.entity.FileEntity;
import ru.innowise.danko.mongogridfileservice.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {

    public final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files/add")
    public String addFile(@RequestParam("name") String name,
                          @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = fileService.uploadFile(name, file);
        return "redirect:/files/" + id;
    }

    @GetMapping("/files/{id}")
    public String getVideo(@PathVariable String id, Model model) throws Exception {
        FileEntity file = fileService.downloadFile(id);
        model.addAttribute("name", file.getName());
        model.addAttribute("url", "/files/stream/" + id);
        return "files";
    }

    @GetMapping("/files/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        FileEntity file = fileService.downloadFile(id);
        FileCopyUtils.copy(file.getStream(), response.getOutputStream());
    }

}
