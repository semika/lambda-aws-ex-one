package com.semika.aws.product.service;

import com.semika.aws.product.model.Product;
import com.semika.aws.product.model.ProductDto;
import com.semika.aws.util.BiConverter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductConverter implements BiConverter<Product, ProductDto> {

    private static ProductConverter instance;

    private ProductConverter() {}

    public static ProductConverter getInstance() {
        if (instance == null) {
            instance = new ProductConverter();
        }
        return instance;
    }

    @Override
    public ProductDto domainToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }

    @Override
    public List<ProductDto> toList(List<Product> products) {
        return products
                .stream()
                .map((Product product) -> this.domainToDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public Product dtoToDomain(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId() == null ? UUID.randomUUID().toString() : productDto.getId())
                .name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .build();
    }

    @Override
    public List<Product> fromList(List<ProductDto> productDtos) {
        return productDtos.stream()
                .map((ProductDto productDto) -> this.dtoToDomain(productDto))
                .collect(Collectors.toList());
    }
}
