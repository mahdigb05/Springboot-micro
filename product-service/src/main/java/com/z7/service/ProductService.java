package com.z7.service;


import com.z7.dto.ProductRequest;
import com.z7.dto.ProductResponse;
import com.z7.model.Product;
import com.z7.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("product with id {} is saved to the db", product.getId());
        return mapProductToResponse(product);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream().map(this::mapProductToResponse).toList();
    }

    private ProductResponse mapProductToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getId())
                .description(product.getDescription())
                .build();

    }

}
