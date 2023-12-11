package com.microservices.productservices.Controller;

import com.microservices.productservices.DTO.ProductRequest;
import com.microservices.productservices.DTO.ProductResponse;
import com.microservices.productservices.Service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
