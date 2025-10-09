package com.example.franchise_api.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseResponseDTO {
    private Long id;
    private String name;
    private String owner;
    private String country;
    private String description;
    
    @Builder.Default
    private List<String> branches = new ArrayList<>();
}
