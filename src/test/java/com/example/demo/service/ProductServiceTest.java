package com.example.demo.service;

import com.example.demo.controller.request.CreateProductRequest;
import com.example.demo.controller.request.UpdateProductRequest;
import com.example.demo.controller.response.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductStatus;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ProductServiceTest {
    private ProductRepository productRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductImpl(productRepository, objectMapper);
    }

    @Test
    void createProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setProductName("test");
        request.setProductStatus(ProductStatus.VAR);
        request.setStock(1);
        request.setUnitPrice(123.0);
        request.setOrder(null);

        Product savedproduct = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);
        ProductResponse productResponse = new ProductResponse("test", 1, ProductStatus.VAR, 123.0);
        Product product = objectMapper.convertValue(request, Product.class);
        Mockito.when(productRepository.save(product)).thenReturn(savedproduct);

        ProductResponse response = productService.createProduct(request);

        assertEquals(response, productResponse);
    }

    @Test
    void getByProductId() {
        int id = 1;
        Product product = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);
        Product productResponse = new Product(id, "test", 1, ProductStatus.VAR, 123.0, null);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product response = productService.getByProductId(id);
        assertEquals(response, productResponse);
        Mockito.verify(productRepository).findById(id);

    }

    @Test
    void getByProductId_whenProductIdDoesNotExists_itShouldThrowsProductNotFoundException() {
        int id = 1;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () ->
                productService.getProductId(id));

        Mockito.verify(productRepository).findById(id);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    void updateProduct() {
        int id = 1;
        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductName("test-update");
        request.setProductStatus(ProductStatus.VAR);
        request.setStock(2);
        request.setUnitPrice(125.0);
        Product product = new Product(1, "test", 2, ProductStatus.VAR, 123.0, new ArrayList<>());
        Product updateUser = new Product(id, "test-update", 2, ProductStatus.VAR, 125.0, new ArrayList<>());
        Product savedProduct = new Product(id, "test-update", 2, ProductStatus.VAR, 125.0, new ArrayList<>());
        ProductResponse productResponse = new ProductResponse(request.getProductName(), request.getStock(), request.getProductStatus(), request.getUnitPrice());


        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(updateUser)).thenReturn(savedProduct);

        ProductResponse response = productService.updateProduct(id, request);

        assertEquals(response, productResponse);

        Mockito.verify(productRepository).findById(id);
        Mockito.verify(productRepository).save(updateUser);

    }

    @Test
    void updateProduct_whenProductIdDoesNotExists_itShouldThrowsProductNotFoundException() {
        int id = 1;
        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductName("test-update");
        request.setProductStatus(ProductStatus.VAR);
        request.setStock(2);
        request.setUnitPrice(125.0);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () -> productService.getByProductId(id));

        Mockito.verify(productRepository).findById(id);
        Mockito.verifyNoMoreInteractions(productRepository);

    }

    @Test
    void deleteProduct() {
        int id = 1;
        Product product = new Product(1, "test", 2, ProductStatus.VAR, 123.0, new ArrayList<>());

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.deleteProduct(id);

        Mockito.verify(productRepository).findById(id);
        Mockito.verify(productRepository).deleteById(id);

    }

    @Test
    void deleteProduct_whenProductIdDoesNotExists_itShouldThrowsProductNotFoundException() {
        int id = 1;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () -> productService.deleteProduct(id));

        Mockito.verify(productRepository).findById(id);
        Mockito.verifyNoMoreInteractions(productRepository);

    }

    @Test
    void getProductId() {
        int id = 1;
        Product product = new Product(1, "test", 2, ProductStatus.VAR, 123.0, new ArrayList<>());

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductResponse response = new ProductResponse("test", 2, ProductStatus.VAR, 123.0);

        ProductResponse productResponse = productService.getProductId(id);

        assertEquals(response, productResponse);

        Mockito.verify(productRepository).findById(id);
    }

    @Test
    void getProductId_whenProductIdDoesNotExists_itShouldThrowsProductNotFoundException() {
        int id = 1;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () -> productService.getProductId(id));

        Mockito.verify(productRepository).findById(id);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    void getAll() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(1, "test-1", 2, ProductStatus.VAR, 122.0, new ArrayList<>());
        Product product2 = new Product(2, "test-2", 3, ProductStatus.VAR, 123.0, new ArrayList<>());
        Product product3 = new Product(3, "test-3", 4, ProductStatus.VAR, 124.0, new ArrayList<>());
        products.add(product1);
        products.add(product2);
        products.add(product3);
        List<ProductResponse> responseList = products.stream().map(ProductResponse::from).collect(Collectors.toList());
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<ProductResponse> response = productService.getAll();

        assertEquals(response, responseList);

        Mockito.verify(productRepository).findAll();

    }
}