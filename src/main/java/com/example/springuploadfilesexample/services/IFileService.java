package com.example.springuploadfilesexample.services;

import com.example.springuploadfilesexample.entities.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IFileService {

    List<File> uploadFiles(MultipartFile[] files);
    List<File> getListFiles();
    Resource load(String filename);

}
