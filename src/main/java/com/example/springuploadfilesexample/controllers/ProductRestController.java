package com.example.springuploadfilesexample.controllers;

import com.example.springuploadfilesexample.entities.Product;
import com.example.springuploadfilesexample.services.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final IProductService productService;
    private final ObjectMapper objectMapper;

    //To pass the Json and Multipart in the POST method we need to mention our content type in the consume part.
   // And then we convert the String param that hold the product info to a product pojo using ObjectMapper.
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product add(@RequestParam String productString, @RequestParam MultipartFile[] images) throws JsonProcessingException {
        Product product = objectMapper.readValue(productString, Product.class);
        return productService.add(product,images);
    }

    @GetMapping("/{idProduct}")
    public Product getProductById(@PathVariable long idProduct) {
        return productService.findById(idProduct);
    }






}
