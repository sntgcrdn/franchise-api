package com.example.franchise_api.service;

import org.springframework.stereotype.Service;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.mapper.BranchMapper;
import com.example.franchise_api.repository.BranchRepository;
import com.example.franchise_api.repository.FranchiseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {
    
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public BranchService(BranchRepository branchRepository, FranchiseRepository franchiseRepository) {
        this.branchRepository = branchRepository;
        this.franchiseRepository = franchiseRepository;
    }

    public List<BranchResponseDTO> getAllBranches() {
        return branchRepository.findAll()
                .stream()
                .map(BranchMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BranchResponseDTO getBranchById(Long id) {
        return branchRepository.findById(id)
                .map(BranchMapper::toResponse)
                .orElse(null);
    }

    public BranchResponseDTO createBranch(BranchRequestDTO dto) {
        Franchise franchise = franchiseRepository.findById(dto.getFranchiseId())
                .orElseThrow(() -> new RuntimeException("Franchise not found with ID: " + dto.getFranchiseId()));

        Branch branch = BranchMapper.toEntity(dto, franchise);
        Branch saved = branchRepository.save(branch);
        return BranchMapper.toResponse(saved);
    }

    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
