package com.example.franchise_api.controller;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() {
        List<BranchResponseDTO> branches = branchService.getAllBranches();
        return branches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(@PathVariable Long id) {
        return Optional.ofNullable(branchService.getBranchById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO dto) {
        return ResponseEntity.ok(branchService.createBranch(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(@PathVariable Long id, @RequestBody BranchRequestDTO dto) {
        return Optional.ofNullable(branchService.updateBranch(id, dto))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        try {
            branchService.deleteBranch(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/updatename")
    public ResponseEntity<BranchResponseDTO> updateBranchName(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        String newName = requestBody.get("name");

        return Optional.ofNullable(branchService.updateBranchName(id, newName))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
