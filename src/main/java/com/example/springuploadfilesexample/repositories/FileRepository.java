package com.example.springuploadfilesexample.repositories;

import com.example.springuploadfilesexample.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}