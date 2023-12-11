package com.microservices.productservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.productservices.DTO.ProductRequest;
import com.microservices.productservices.DTO.ProductResponse;
import com.microservices.productservices.Model.Product;
import com.microservices.productservices.Repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServicesApplicationTest {

    //an IDE bug, its working normally
    @Autowired
    private MockMvc mockMvc;

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }
    //IDE bug, working normally
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .name("Samsung")
                .description("A good samsung Phone")
                .price(BigDecimal.valueOf(1000))
                .build();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))
                .andExpect(status().isCreated());

        Assertions.assertThat(productRepository.findAll().size() == 1).isTrue();
        productRepository.deleteAll();
    }

    @Test
    void shouldGetAllProductsAndReturnNone() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        String expected = "[]";
        Assertions.assertThat(content).isEqualTo(expected);
    }

    @Test
    void shouldGetAllProductAndReturnTwo() throws Exception {
        Product product1 = Product.builder()
                .name("Samsung")
                .description("A good samsung Phone")
                .price(BigDecimal.valueOf(1000))
                .build();
        Product product2 = Product.builder()
                .name("Samsung2")
                .description("A good samsung Phone")
                .price(BigDecimal.valueOf(1000))
                .build();
        productRepository.save(product1);
        productRepository.save(product2);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Assertions.assertThat(productRepository.findAll().size() == 2).isTrue();
        Assertions.assertThat(productRepository.findAll().size()).isNotNull();
        Assertions.assertThat(productRepository.findAll().size() != 2).isFalse();
    }

}