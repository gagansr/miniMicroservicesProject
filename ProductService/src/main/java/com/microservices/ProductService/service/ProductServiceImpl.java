package com.microservices.ProductService.service;

import com.microservices.ProductService.entity.Product;
import com.microservices.ProductService.exceptionHandling.ProductServiceCustomException;
import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;
import com.microservices.ProductService.repositoty.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Saving product to DB");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("Product has been Saved to DB {}",product.getProductId());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) throws ProductServiceCustomException {
        Optional<Product> product = Optional.ofNullable(productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductServiceCustomException("Unable to find the product with the given product Id.","PRODUCT_NOT_FOUND")));

        ProductResponse productResponse = new ProductResponse();

        BeanUtils.copyProperties(product.get(),productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) throws ProductServiceCustomException {

        log.info("Updating quantity for {} with quantity - {}",productId,quantity);

        Product product = productRepository.findById(productId)
                .orElseThrow( () ->
                        new ProductServiceCustomException("Product with not available","PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product not in stock for required quantity.","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        log.info("Quantity updated for {}, is {}",product.getProductName(),product.getQuantity());
    }
}
