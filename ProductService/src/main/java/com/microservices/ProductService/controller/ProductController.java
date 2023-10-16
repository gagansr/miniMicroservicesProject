package com.microservices.ProductService.controller;

import com.microservices.ProductService.exceptionHandling.ProductServiceCustomException;
import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;
import com.microservices.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) throws ProductServiceCustomException {
        ProductResponse productResponse = productService.getProductById(productId);

        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{productId}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Long productId, @RequestParam Long quantity) throws ProductServiceCustomException {
        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
