package com.job.application.controller;

import com.job.application.ProductDTO;
import com.job.application.entity.Category;
import com.job.application.entity.Product;
import com.job.application.repository.CategoryRepository;
import com.job.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PatchMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(productRepository.save(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(required = false) Long quantityCategories) {
        return ResponseEntity.ok().body(productRepository.findByQuantityCategories(quantityCategories));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getProductsByCategories() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @GetMapping("/prices")
    public ResponseEntity<List<Product>> getProductsOrderByPrices() {
        return ResponseEntity.ok().body(productRepository.findAll(Sort.by(Sort.Direction.ASC, "amount")));
    }

}
