package com.example.franchise_api.service;

import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.repository.FranchiseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java .util.Optional;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;

    public FranchiseService(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    public Optional<Franchise> getFranchiseById(Long id) {
        return franchiseRepository.findById(id);
    }

    public Franchise saveFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public void deleteFranchise(Long id) {
        franchiseRepository.deleteById(id);
    }
}
