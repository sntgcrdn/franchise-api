package com.example.franchise_api.controller;

import com.example.franchise_api.dto.response.TopStockProductResponseDTO;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.service.FranchiseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping
    public List<Franchise> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchiseById(@PathVariable Long id) {
        return franchiseService.getFranchiseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Franchise createFranchise(@RequestBody Franchise franchise) {
        return franchiseService.saveFranchise(franchise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchiseDetails) {
        return franchiseService.getFranchiseById(id).map(franchise -> {
            franchise.setName(franchiseDetails.getName());
            franchise.setOwner(franchiseDetails.getOwner());
            Franchise updated = franchiseService.saveFranchise(franchise);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchise(@PathVariable Long id) {
        franchiseService.deleteFranchise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/top-stock-products")
    public List<TopStockProductResponseDTO> getTopStockProducts(@PathVariable Long id) {
        return franchiseService.getTopStockProducts(id);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Franchise> updateFranchiseName(
            @PathVariable Long id,
            @RequestBody String newName) {
        return franchiseService.updateFranchiseName(id, newName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}