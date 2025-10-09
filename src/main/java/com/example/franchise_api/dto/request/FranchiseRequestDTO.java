package com.example.franchise_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FranchiseRequestDTO {
    @NotBlank(message = "The franchise name is required.")
    @Size(max = 100, message = "The franchise name cannot exceed 100 characters.")
    private String name;
    private String country;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

}
