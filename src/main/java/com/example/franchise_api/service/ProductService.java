package com.example.franchise_api.service;

import com.example.franchise_api.dto.request.ProductRequestDTO;
import com.example.franchise_api.dto.request.ProductStockDTO;
import com.example.franchise_api.dto.response.ProductResponseDTO;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.mapper.ProductMapper;
import com.example.franchise_api.repository.ProductRepository;
import com.example.franchise_api.repository.BranchRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public ProductService(ProductRepository productRepository, BranchRepository branchRepository) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponse)
                .orElse(null);
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + dto.getBranchId()));

        Product product = ProductMapper.toEntity(dto, branch);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        return productRepository.findById(id).map(product -> {
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            if (dto.getCategory() != null) {
                product.setCategory(dto.getCategory());
            }
    
            // Si necesitas actualizar la sucursal:
            if (dto.getBranchId() != null) {
                Branch branch = branchRepository.findById(dto.getBranchId())
                        .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + dto.getBranchId()));
                product.setBranch(branch);
            }
    
            Product saved = productRepository.save(product);
            return ProductMapper.toResponse(saved);
        }).orElse(null);
    }

    public ProductResponseDTO updateProductStock(Long productId, ProductStockDTO dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }

        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }
}
