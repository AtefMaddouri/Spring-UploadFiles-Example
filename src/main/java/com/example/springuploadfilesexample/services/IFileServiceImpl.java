package com.example.springuploadfilesexample.services;

import com.example.springuploadfilesexample.controllers.FilesRestController;
import com.example.springuploadfilesexample.entities.File;
import com.example.springuploadfilesexample.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class IFileServiceImpl implements IFileService {

    private final FileRepository fileRepository;
    private final Path root = Paths.get(".\\src\\main\\resources\\uploads");

    @Override
    public List<File> uploadFiles(MultipartFile[] files) {
          Arrays.asList(files).forEach(this::save);
          return mapIntoFiles(files);
    }
    @Override
    public List<File> getListFiles() {
        return loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FilesRestController.class, "getFile", path.getFileName().toString()).build().toString();
            return File.builder().name(filename).url(url).build();
        }).collect(Collectors.toList());
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the File!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    private List<File> mapIntoFiles(MultipartFile[] files) {
       return Arrays.asList(files).stream()
               .map(multipartFile ->{
                     String filename = multipartFile.getOriginalFilename();
                     String url = MvcUriComponentsBuilder.fromMethodName(FilesRestController.class, "getFile", multipartFile.getOriginalFilename()).build().toString();
                     return File.builder().name(filename).url(url).build();
               })
               .collect(Collectors.toList());
    }

    private MultipartFile save(MultipartFile file)  {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

        } catch (Exception e) {
            throw new RuntimeException("Could not store the File. Error: " + e.getMessage());
        }
          return file;
    }
    private Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    private void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }



}

