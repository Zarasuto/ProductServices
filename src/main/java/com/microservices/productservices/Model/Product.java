package com.microservices.productservices.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value="product")
public class Product {

    @Id
    private String id;

    @Field("name")
    private String name;
    @Field("description")
    private String description;
    @Field("price")
    private BigDecimal price;
}
