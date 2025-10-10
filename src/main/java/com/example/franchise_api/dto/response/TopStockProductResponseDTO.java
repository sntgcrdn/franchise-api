package com.example.franchise_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopStockProductResponseDTO {
    private Long productId;
    private String productName;
    private Integer stock;
    private Long branchId;
    private String branchName;
}
