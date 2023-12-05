package com.example.springuploadfilesexample.controllers;

import com.example.springuploadfilesexample.entities.File;
import com.example.springuploadfilesexample.services.IFileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilesRestController {

    private final IFileService fileService;

    @PostMapping("/upload")
    public List<File> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        return fileService.uploadFiles(files);
    }

    @GetMapping("/files/{filename:.+}")
    public Resource getFile(@PathVariable String filename) {
        return fileService.load(filename);
    }

    @GetMapping("/files")
    public List<File> getListFiles() {
        return fileService.getListFiles();
    }

}
