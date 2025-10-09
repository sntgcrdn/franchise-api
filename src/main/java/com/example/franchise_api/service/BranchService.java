package com.example.franchise_api.service;

import org.springframework.stereotype.Service;

import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.repository.BranchRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    
    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
