package com.semika.aws.product.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.employee.handler.EmployeeAddHandler;
import com.semika.aws.employee.service.EmployeeService;
import com.semika.aws.model.response.ThaproResponseBuilder;
import com.semika.aws.product.model.ProductDto;
import com.semika.aws.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductAddHandler implements RequestHandler<ProductDto, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAddHandler.class);
    private ProductService productService;

    public ProductAddHandler() {
        this.productService = ProductService.getInstance();
    }

    @Override
    public String handleRequest(ProductDto productDto, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(productDto.toString());

        if (productDto.getId() == null) {
            productService.save(productDto);
        } else {
            productService.update(productDto);
        }
        String jsonResponse = ThaproResponseBuilder
                .create()
                .withMessage("Product saved successfully..")
                .withoutData()
                .toJson();

        logger.log("Product saved successfully");

        return jsonResponse;
    }
}
