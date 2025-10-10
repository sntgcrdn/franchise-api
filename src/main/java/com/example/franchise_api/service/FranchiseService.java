package com.example.franchise_api.service;

import com.example.franchise_api.dto.response.TopStockProductResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.repository.FranchiseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;

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
    public List<TopStockProductResponseDTO> getTopStockProducts(Long franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franchise not found with ID: " + franchiseId));

        List<TopStockProductResponseDTO> topProducts = new ArrayList<>();

        for (Branch branch : franchise.getBranches()) {
            branch.getProducts().stream()
                    .max(Comparator.comparingInt(Product::getStock))
                    .ifPresent(product -> topProducts.add(
                            new TopStockProductResponseDTO(
                                    product.getId(),
                                    product.getName(),
                                    product.getStock(),
                                    branch.getId(),
                                    branch.getName()
                            )
                    ));
        }

        return topProducts;
    }
}
