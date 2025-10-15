package com.example.franchise_api.controller;

import com.example.franchise_api.dto.response.TopStockProductResponseDTO;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.service.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FranchiseControllerTest {

    @Mock
    private FranchiseService franchiseService;

    @InjectMocks
    private FranchiseController franchiseController;

    private Franchise franchise;
    private TopStockProductResponseDTO topProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franquicia Central");
        franchise.setOwner("Santiago");
        franchise.setCountry("Colombia");

        topProduct = new TopStockProductResponseDTO(1L, "Producto A", 100, 2L, "Sucursal Medellín");
    }

    @Test
    void testGetAllFranchises_ReturnsList() {
        when(franchiseService.getAllFranchises()).thenReturn(List.of(franchise));

        List<Franchise> response = franchiseController.getAllFranchises();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Franquicia Central", response.get(0).getName());
        verify(franchiseService, times(1)).getAllFranchises();
    }

    @Test
    void testGetFranchiseById_Found() {
        when(franchiseService.getFranchiseById(1L)).thenReturn(Optional.of(franchise));

        ResponseEntity<Franchise> response = franchiseController.getFranchiseById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Franquicia Central", response.getBody().getName());
        verify(franchiseService).getFranchiseById(1L);
    }

    @Test
    void testGetFranchiseById_NotFound() {
        when(franchiseService.getFranchiseById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Franchise> response = franchiseController.getFranchiseById(99L);

        assertEquals(404, response.getStatusCode().value());
        verify(franchiseService).getFranchiseById(99L);
    }

    @Test
    void testCreateFranchise() {
        when(franchiseService.saveFranchise(any(Franchise.class))).thenReturn(franchise);

        Franchise response = franchiseController.createFranchise(franchise);

        assertNotNull(response);
        assertEquals("Franquicia Central", response.getName());
        verify(franchiseService).saveFranchise(any(Franchise.class));
    }

    @Test
    void testUpdateFranchise_Found() {
        Franchise updatedFranchise = new Franchise();
        updatedFranchise.setName("Franquicia Actualizada");
        updatedFranchise.setOwner("Nuevo Dueño");

        when(franchiseService.getFranchiseById(1L)).thenReturn(Optional.of(franchise));
        when(franchiseService.saveFranchise(any(Franchise.class))).thenReturn(updatedFranchise);

        ResponseEntity<Franchise> response = franchiseController.updateFranchise(1L, updatedFranchise);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Franquicia Actualizada", response.getBody().getName());
        verify(franchiseService).saveFranchise(any(Franchise.class));
    }

    @Test
    void testUpdateFranchise_NotFound() {
        when(franchiseService.getFranchiseById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Franchise> response = franchiseController.updateFranchise(99L, franchise);

        assertEquals(404, response.getStatusCode().value());
        verify(franchiseService).getFranchiseById(99L);
    }

    @Test
    void testDeleteFranchise() {
        doNothing().when(franchiseService).deleteFranchise(1L);

        ResponseEntity<Void> response = franchiseController.deleteFranchise(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(franchiseService).deleteFranchise(1L);
    }

    @Test
    void testGetTopStockProducts() {
        when(franchiseService.getTopStockProducts(1L)).thenReturn(List.of(topProduct));

        List<TopStockProductResponseDTO> response = franchiseController.getTopStockProducts(1L);

        assertEquals(1, response.size());
        assertEquals("Producto A", response.get(0).getProductName());
        verify(franchiseService).getTopStockProducts(1L);
    }

    @Test
    void testUpdateFranchiseName_Found() {
        when(franchiseService.updateFranchiseName(1L, "Nuevo Nombre")).thenReturn(Optional.of(franchise));

        ResponseEntity<Franchise> response = franchiseController.updateFranchiseName(1L, Map.of("name", "Nuevo Nombre"));

        assertEquals(200, response.getStatusCode().value());
        verify(franchiseService).updateFranchiseName(1L, "Nuevo Nombre");
    }

    @Test
    void testUpdateFranchiseName_NotFound() {
        when(franchiseService.updateFranchiseName(99L, "Nombre")).thenReturn(Optional.empty());

        ResponseEntity<Franchise> response = franchiseController.updateFranchiseName(99L, Map.of("name", "Nombre"));

        assertEquals(404, response.getStatusCode().value());
        verify(franchiseService).updateFranchiseName(99L, "Nombre");
    }
}