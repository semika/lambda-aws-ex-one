package com.semika.aws.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {
    private String id;
    private String name;
    private String category;
    private Double price;
}
