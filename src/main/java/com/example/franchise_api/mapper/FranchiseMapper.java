package com.example.franchise_api.mapper;

import java.util.stream.Collectors;

import com.example.franchise_api.dto.request.FranchiseRequestDTO;
import com.example.franchise_api.dto.response.FranchiseResponseDTO;
import com.example.franchise_api.entity.Franchise;

public class FranchiseMapper {
    
    // Convierte de entidad a DTO de respuesta
    public static FranchiseResponseDTO toResponse(Franchise franchise) {
        if (franchise == null) return null;

        return FranchiseResponseDTO.builder()
            .id(franchise.getId())
            .name(franchise.getName())
            .owner(franchise.getOwner())
            .country(franchise.getCountry())
            .branches(franchise.getBranches() != null
                    ? franchise.getBranches().stream()
                        .map(branch -> branch.getName())
                        .collect(Collectors.toList())
                    : null)
            .build();
    }

    // Convierte de DTO de request a entidad
    public static Franchise toEntity(FranchiseRequestDTO dto) {
        if (dto == null) return null;

        return Franchise.builder()
                .name(dto.getName())
                .country(dto.getCountry())
                .build();
    }
}
