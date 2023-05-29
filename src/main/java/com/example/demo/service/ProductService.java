package com.example.demo.service;

import com.example.demo.controller.request.CreateProductRequest;
import com.example.demo.controller.request.UpdateProductRequest;
import com.example.demo.controller.response.ProductResponse;
import com.example.demo.entities.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);

    Product getByProductId(int id);

    ProductResponse updateProduct(int id, UpdateProductRequest request);

    void deleteProduct(int id);


    ProductResponse getProductId(int id);

    List<ProductResponse> getAll();
}
