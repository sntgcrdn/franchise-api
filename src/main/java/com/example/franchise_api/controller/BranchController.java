package com.example.franchise_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.service.BranchService;

import java.util.List;

@RestController
@RequestMapping ("/branches")
public class BranchController {
    
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public List<BranchResponseDTO> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(@PathVariable Long id) {
        BranchResponseDTO branch = branchService.getBranchById(id);
        return branch != null ? ResponseEntity.ok(branch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO dto) {
        return ResponseEntity.ok(branchService.createBranch(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchRequestDTO dto) {
        BranchResponseDTO updatedBranch = branchService.updateBranch(id, dto);
        return updatedBranch != null
                ? ResponseEntity.ok(updatedBranch)
                : ResponseEntity.notFound().build();
}
}
