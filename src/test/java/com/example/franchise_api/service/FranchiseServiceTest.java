package com.example.franchise_api.service;

import com.example.franchise_api.dto.response.TopStockProductResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    private Franchise franchise;
    private Branch branch1;
    private Branch branch2;
    private Product productA;
    private Product productB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productA = new Product();
        productA.setId(1L);
        productA.setName("Café de origen");
        productA.setStock(50);

        productB = new Product();
        productB.setId(2L);
        productB.setName("Té verde");
        productB.setStock(30);

        branch1 = new Branch();
        branch1.setId(1L);
        branch1.setName("Sucursal Medellín");
        branch1.setProducts(List.of(productA, productB));

        branch2 = new Branch();
        branch2.setId(2L);
        branch2.setName("Sucursal Bogotá");
        branch2.setProducts(List.of(productB));

        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franquicia Central");
        franchise.setOwner("Santiago");
        franchise.setCountry("Colombia");
        franchise.setBranches(List.of(branch1, branch2));
    }

    @Test
    void testGetAllFranchises() {
        when(franchiseRepository.findAll()).thenReturn(List.of(franchise));

        List<Franchise> result = franchiseService.getAllFranchises();

        assertEquals(1, result.size());
        assertEquals("Franquicia Central", result.get(0).getName());
        verify(franchiseRepository).findAll();
    }

    @Test
    void testGetFranchiseById_Found() {
        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));

        Optional<Franchise> result = franchiseService.getFranchiseById(1L);

        assertTrue(result.isPresent());
        assertEquals("Franquicia Central", result.get().getName());
        verify(franchiseRepository).findById(1L);
    }

    @Test
    void testGetFranchiseById_NotFound() {
        when(franchiseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Franchise> result = franchiseService.getFranchiseById(99L);

        assertTrue(result.isEmpty());
        verify(franchiseRepository).findById(99L);
    }

    @Test
    void testSaveFranchise() {
        when(franchiseRepository.save(franchise)).thenReturn(franchise);

        Franchise result = franchiseService.saveFranchise(franchise);

        assertNotNull(result);
        assertEquals("Franquicia Central", result.getName());
        verify(franchiseRepository).save(franchise);
    }

    @Test
    void testDeleteFranchise() {
        doNothing().when(franchiseRepository).deleteById(1L);

        franchiseService.deleteFranchise(1L);

        verify(franchiseRepository).deleteById(1L);
    }

    @Test
    void testUpdateFranchiseName_Found() {
        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(franchise);

        Optional<Franchise> result = franchiseService.updateFranchiseName(1L, "Nuevo Nombre");

        assertTrue(result.isPresent());
        assertEquals("Nuevo Nombre", result.get().getName());
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void testUpdateFranchiseName_NotFound() {
        when(franchiseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Franchise> result = franchiseService.updateFranchiseName(99L, "Nombre");

        assertTrue(result.isEmpty());
        verify(franchiseRepository).findById(99L);
    }

    @Test
    void testGetTopStockProducts_ReturnsList() {
        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));

        List<TopStockProductResponseDTO> result = franchiseService.getTopStockProducts(1L);

        assertEquals(2, result.size());
        assertEquals("Sucursal Medellín", result.get(0).getBranchName());
        assertEquals("Café de origen", result.get(0).getProductName());
        verify(franchiseRepository).findById(1L);
    }

    @Test
    void testGetTopStockProducts_FranchiseNotFound() {
        when(franchiseRepository.findById(99L)).thenReturn(Optional.empty());

        List<TopStockProductResponseDTO> result = franchiseService.getTopStockProducts(99L);

        assertTrue(result.isEmpty());
        verify(franchiseRepository).findById(99L);
    }
}
