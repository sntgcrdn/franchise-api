package com.example.franchise_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponseDTO {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String franchiseName;
    private FranchiseSummaryDTO franchise;
}
