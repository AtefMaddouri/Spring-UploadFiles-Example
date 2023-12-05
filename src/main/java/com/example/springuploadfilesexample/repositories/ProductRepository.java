package com.example.springuploadfilesexample.repositories;

import com.example.springuploadfilesexample.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}