package com.microservices.productservices.Service;

import com.microservices.productservices.DTO.ProductRequest;
import com.microservices.productservices.DTO.ProductResponse;
import com.microservices.productservices.Model.Product;
import com.microservices.productservices.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    //Autowires the dependency using constructor injector and using lombok's @RequiredArgsConstructor to automate it
    private final ProductRepository productRepository;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = new Product().builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    /**
     * Returns all Products inside the database
     * @return
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        //using Stream, map the Product list into ProductResponse List using lambda and stream
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
