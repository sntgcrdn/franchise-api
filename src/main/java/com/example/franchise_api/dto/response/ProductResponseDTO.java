package com.example.franchise_api.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private String category;
    private String branchName;
    private Integer stock;
    private BranchSummaryDTO branch;
}