package com.example.demo.controller;

import com.example.demo.controller.request.CreateProductRequest;
import com.example.demo.controller.request.UpdateProductRequest;
import com.example.demo.controller.response.ProductResponse;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable int id,@RequestBody UpdateProductRequest request){
        return productService.updateProduct(id,request);
    }

    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(int id){
        return productService.deleteProduct(id);
    }

    // Todo ürün silme güncelleme işlemi
}
