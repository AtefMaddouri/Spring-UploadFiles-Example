package com.example.springuploadfilesexample.services;

import com.example.springuploadfilesexample.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    Product add(Product product, MultipartFile[] images);

    Product findById(long idProduct);

    List<Product> findAll();

    void delete(long idProduct);


}
