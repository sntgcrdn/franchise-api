package com.example.franchise_api.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranchiseSummaryDTO {
    private Long id;
    private String name;
}
