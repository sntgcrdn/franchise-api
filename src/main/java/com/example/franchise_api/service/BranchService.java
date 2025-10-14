package com.example.franchise_api.service;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.mapper.BranchMapper;
import com.example.franchise_api.repository.BranchRepository;
import com.example.franchise_api.repository.FranchiseRepository;
import org.springframework.stereotype.Service;

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
        return franchiseRepository.findById(dto.getFranchiseId())
                .map(franchise -> {
                    Branch branch = BranchMapper.toEntity(dto, franchise);
                    return BranchMapper.toResponse(branchRepository.save(branch));
                })
                .orElseThrow(() -> new RuntimeException("Franchise not found with ID: " + dto.getFranchiseId()));
    }

    public BranchResponseDTO updateBranch(Long id, BranchRequestDTO dto) {
        return branchRepository.findById(id)
                .map(branch -> {
                    branch.setName(dto.getName());
                    branch.setCity(dto.getCity());
                    branch.setAddress(dto.getAddress());

                    if (dto.getFranchiseId() != null) {
                        Franchise franchise = franchiseRepository.findById(dto.getFranchiseId())
                                .orElseThrow(() -> new RuntimeException("Franchise not found with ID: " + dto.getFranchiseId()));
                        branch.setFranchise(franchise);
                    }

                    return BranchMapper.toResponse(branchRepository.save(branch));
                })
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + id));
    }

    public void deleteBranch(Long id) {
        branchRepository.findById(id)
                .ifPresentOrElse(
                        branchRepository::delete,
                        () -> { throw new RuntimeException("Branch not found with ID: " + id); }
                );
    }

    public BranchResponseDTO updateBranchName(Long id, String newName) {
        return branchRepository.findById(id)
                .map(branch -> {
                    branch.setName(newName);
                    return BranchMapper.toResponse(branchRepository.save(branch));
                })
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + id));
    }
}