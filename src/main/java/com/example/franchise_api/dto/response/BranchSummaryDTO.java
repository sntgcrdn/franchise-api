package com.example.franchise_api.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchSummaryDTO {
    private Long id;
    private String name;
    private String city;
}
