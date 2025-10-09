package com.example.franchise_api.mapper;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.dto.response.FranchiseSummaryDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;

public class BranchMapper {
    public static BranchResponseDTO toResponse(Branch branch) {
        if (branch == null) return null;

        Franchise franchise = branch.getFranchise();
        FranchiseSummaryDTO franchiseSummary = null;

        if (franchise != null) {
            franchiseSummary = FranchiseSummaryDTO.builder()
                    .id(franchise.getId())
                    .name(franchise.getName())
                    .build();
        }

        return BranchResponseDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .city(branch.getCity())
                .address(branch.getAddress())
                .franchiseName(franchise != null ? franchise.getName() : null)
                .franchise(franchiseSummary)
                .build();
    }

    public static Branch toEntity(BranchRequestDTO dto, Franchise franchise) {
        if (dto == null) return null;
        return Branch.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .address(dto.getAddress())
                .franchise(franchise)
                .build();
    }
}
