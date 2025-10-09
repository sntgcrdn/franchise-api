package com.example.franchise_api.mapper;

import com.example.franchise_api.dto.request.ProductRequestDTO;
import com.example.franchise_api.dto.response.ProductResponseDTO;
import com.example.franchise_api.dto.response.BranchSummaryDTO;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.entity.Branch;

public class ProductMapper {
    
    public static ProductResponseDTO toResponse(Product product) {
        if (product == null) return null;

        Branch branch = product.getBranch();
        BranchSummaryDTO branchSummary = null;
        if (branch != null) {
            branchSummary = BranchSummaryDTO.builder()
                    .id(branch.getId())
                    .name(branch.getName())
                    .city(branch.getCity())
                    .build();
        }

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .branchName(branch != null ? branch.getName() : null)
                .branch(branchSummary)
                .build();
    }

    public static Product toEntity(ProductRequestDTO dto, Branch branch) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .branch(branch)
                .build();
    }
}
