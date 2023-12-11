package com.microservices.productservices.Service;

import com.microservices.productservices.DTO.ProductRequest;
import com.microservices.productservices.DTO.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
}
