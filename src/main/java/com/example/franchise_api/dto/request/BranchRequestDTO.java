package com.example.franchise_api.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequestDTO {
    private String name;
    private String city;
    private String address;
    private Long franchiseId;
}