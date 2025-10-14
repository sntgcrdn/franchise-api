package com.example.franchise_api.service;

import com.example.franchise_api.dto.response.TopStockProductResponseDTO;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.repository.FranchiseRepository;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public Optional<Franchise> updateFranchiseName(Long id, String newName) {
        return franchiseRepository.findById(id)
                .map(franchise -> {
                    franchise.setName(newName);
                    return franchiseRepository.save(franchise);
                });
    }

    public List<TopStockProductResponseDTO> getTopStockProducts(Long franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .map(franchise -> franchise.getBranches()
                        .stream()
                        .map(branch -> branch.getProducts()
                                .stream()
                                .max(Comparator.comparingInt(Product::getStock))
                                .map(product -> new TopStockProductResponseDTO(
                                        product.getId(),
                                        product.getName(),
                                        product.getStock(),
                                        branch.getId(),
                                        branch.getName()
                                ))
                                .orElse(null)
                        )
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }
}