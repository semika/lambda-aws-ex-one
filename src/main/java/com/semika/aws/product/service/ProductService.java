package com.semika.aws.product.service;

import com.semika.aws.common.service.GenericService;
import com.semika.aws.product.model.Product;
import com.semika.aws.product.model.ProductDto;
import com.semika.aws.product.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService implements GenericService<Product, ProductDto> {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    private static ProductService instance = null;

    private ProductService() {
        this.productRepository = ProductRepository.getInstance();
        this.productConverter = ProductConverter.getInstance();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    @Override
    public ProductDto findById(String id) {
        Product product = productRepository.findById(id);
        return productConverter.domainToDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map((Product product) -> productConverter.domainToDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = productConverter.dtoToDomain(productDto);
        product = productRepository.save(product);
        return productConverter.domainToDto(product);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = productConverter.dtoToDomain(productDto);
        product = productRepository.update(product);
        return productConverter.domainToDto(product);
    }
}
