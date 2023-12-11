package com.microservices.productservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//EurekaClient Enabled
@SpringBootApplication
public class ProductServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServicesApplication.class, args);
    }

}
