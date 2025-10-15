package com.example.franchise_api.service;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.repository.BranchRepository;
import com.example.franchise_api.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private BranchService branchService;

    private Franchise franchise;
    private Branch branch;
    private BranchRequestDTO branchRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchise = Franchise.builder()
                .id(1L)
                .name("Franquicia Central")
                .owner("Santiago")
                .country("Colombia")
                .build();

        branch = Branch.builder()
                .id(1L)
                .name("Sucursal Medellín")
                .city("Medellín")
                .address("Cra 43A #1-50")
                .franchise(franchise)
                .build();

        branchRequestDTO = BranchRequestDTO.builder()
                .name("Sucursal Medellín")
                .city("Medellín")
                .address("Cra 43A #1-50")
                .franchiseId(1L)
                .build();
    }

    @Test
    void testGetAllBranches() {
        when(branchRepository.findAll()).thenReturn(List.of(branch));

        List<BranchResponseDTO> result = branchService.getAllBranches();

        assertEquals(1, result.size());
        assertEquals("Sucursal Medellín", result.get(0).getName());
        verify(branchRepository, times(1)).findAll();
    }

    @Test
    void testGetBranchById_Found() {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

        BranchResponseDTO result = branchService.getBranchById(1L);

        assertNotNull(result);
        assertEquals("Sucursal Medellín", result.getName());
    }

    @Test
    void testGetBranchById_NotFound() {
        when(branchRepository.findById(99L)).thenReturn(Optional.empty());

        BranchResponseDTO result = branchService.getBranchById(99L);

        assertNull(result);
    }

    @Test
    void testCreateBranch_Success() {
        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);

        BranchResponseDTO result = branchService.createBranch(branchRequestDTO);

        assertNotNull(result);
        assertEquals("Sucursal Medellín", result.getName());
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    void testCreateBranch_FranchiseNotFound() {
        when(franchiseRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                branchService.createBranch(branchRequestDTO));

        assertEquals("Franchise not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteBranch() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal Medellín");

        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        doNothing().when(branchRepository).delete(branch);

        branchService.deleteBranch(1L);

        verify(branchRepository, times(1)).delete(branch);
    }
}