package com.example.demo.service;

import com.example.demo.controller.request.CreateProductRequest;
import com.example.demo.controller.request.UpdateProductRequest;
import com.example.demo.controller.response.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product(
                request.getId(),
                request.getProductName(),
                request.getStock(),
                request.getProductStatus(),
                request.getUnitPrice(),
                request.getOrder()

        );
        productRepository.save(product);
        ProductResponse productResponse = objectMapper.convertValue(product, ProductResponse.class);
        return productResponse;
    }

    @Override
    public Product getByProductId(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }

        throw new AsgDataNotFoundException(ExceptionType.PRODUCT_DATA_NOT_FOUND);
    }

    @Override
    public ProductResponse updateProduct(int id, UpdateProductRequest request) {
        Product byProductId = getByProductId(id);
        Product product = new Product(
                byProductId.getId(),
                request.getProductName(),
                request.getStock(),
                request.getProductStatus(),
                request.getUnitPrice(),
                byProductId.getOrder()
        );
        productRepository.save(product);
        return ProductResponse.from(product);
    }

    @Override
    public void deleteProduct(int id) {
        getByProductId(id);
        productRepository.deleteById(id);
    }

}
