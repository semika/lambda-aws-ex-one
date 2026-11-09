package com.semika.aws.product.repository;

import com.semika.aws.common.repository.BaseRepository;
import com.semika.aws.product.model.Product;

public class ProductRepository extends BaseRepository<Product> {

    private static ProductRepository instance;

    private ProductRepository() {

    }
    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    public String get() {
        return "Product";
    }
}
