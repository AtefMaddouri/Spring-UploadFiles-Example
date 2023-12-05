package com.example.springuploadfilesexample.services;

import com.example.springuploadfilesexample.entities.Product;
import com.example.springuploadfilesexample.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IProductServiceImp implements IProductService{
    private final ProductRepository productRepository;
    private final IFileService fileService;

    @Override
    public Product add(Product product, MultipartFile[] images) {
       product.setFileList(new ArrayList<>());
       fileService.uploadFiles(images).forEach(file -> product.getFileList().add(file));
       return productRepository.save(product);
    }

    @Override
    public Product findById(long idProduct) {
        return productRepository.findById(idProduct).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(long idProduct) {
        productRepository.deleteById(idProduct);
    }



}
