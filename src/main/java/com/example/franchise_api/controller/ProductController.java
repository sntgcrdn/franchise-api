package com.example.franchise_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.franchise_api.dto.request.ProductRequestDTO;
import com.example.franchise_api.dto.response.ProductResponseDTO;
import com.example.franchise_api.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, dto);
        return updatedProduct != null
                ? ResponseEntity.ok(updatedProduct)
                : ResponseEntity.notFound().build();
    }
}
