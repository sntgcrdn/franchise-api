package com.example.franchise_api.controller;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private BranchResponseDTO branchResponse;
    private BranchRequestDTO branchRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        branchResponse = BranchResponseDTO.builder()
                .id(1L)
                .name("Sucursal Medellín")
                .city("Medellín")
                .address("Cra 43A #1-50")
                .franchiseName("Franquicia Central")
                .build();

        branchRequest = BranchRequestDTO.builder()
                .name("Sucursal Medellín")
                .city("Medellín")
                .address("Cra 43A #1-50")
                .franchiseId(1L)
                .build();
    }

    @Test
    void testGetAllBranches_ReturnsList() {
        when(branchService.getAllBranches()).thenReturn(List.of(branchResponse));

        ResponseEntity<List<BranchResponseDTO>> response = branchController.getAllBranches();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<BranchResponseDTO> body = response.getBody();
        assertNotNull(body, "El cuerpo no debe ser nulo");
        assertEquals(1, body.size());
        assertEquals("Sucursal Medellín", body.get(0).getName());
    }

    @Test
    void testGetAllBranches_ReturnsNoContent() {
        when(branchService.getAllBranches()).thenReturn(List.of());

        ResponseEntity<List<BranchResponseDTO>> response = branchController.getAllBranches();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody(), "El cuerpo debe ser nulo cuando no hay contenido");
    }

    @Test
    void testGetBranchById_Found() {
        when(branchService.getBranchById(1L)).thenReturn(branchResponse);

        ResponseEntity<BranchResponseDTO> response = branchController.getBranchById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        BranchResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("Sucursal Medellín", body.getName());
    }

    @Test
    void testGetBranchById_NotFound() {
        when(branchService.getBranchById(99L)).thenReturn(null);

        ResponseEntity<BranchResponseDTO> response = branchController.getBranchById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateBranch_ReturnsCreatedBranch() {
        when(branchService.createBranch(branchRequest)).thenReturn(branchResponse);

        ResponseEntity<BranchResponseDTO> response = branchController.createBranch(branchRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        BranchResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("Sucursal Medellín", body.getName());
    }

    @Test
    void testUpdateBranch_Found() {
        when(branchService.updateBranch(1L, branchRequest)).thenReturn(branchResponse);

        ResponseEntity<BranchResponseDTO> response = branchController.updateBranch(1L, branchRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        BranchResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("Sucursal Medellín", body.getName());
    }

    @Test
    void testUpdateBranch_NotFound() {
        when(branchService.updateBranch(99L, branchRequest)).thenReturn(null);

        ResponseEntity<BranchResponseDTO> response = branchController.updateBranch(99L, branchRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteBranch_Success() {
        doNothing().when(branchService).deleteBranch(1L);

        ResponseEntity<Void> response = branchController.deleteBranch(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(branchService, times(1)).deleteBranch(1L);
    }

    @Test
    void testDeleteBranch_NotFound() {
        doThrow(new RuntimeException("Branch not found")).when(branchService).deleteBranch(99L);

        ResponseEntity<Void> response = branchController.deleteBranch(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateBranchName_Found() {
        when(branchService.updateBranchName(1L, "Sucursal Nueva")).thenReturn(branchResponse);

        ResponseEntity<BranchResponseDTO> response =
                branchController.updateBranchName(1L, Map.of("name", "Sucursal Nueva"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        BranchResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("Sucursal Medellín", body.getName());
    }

    @Test
    void testUpdateBranchName_NotFound() {
        when(branchService.updateBranchName(99L, "Sucursal Nueva")).thenReturn(null);

        ResponseEntity<BranchResponseDTO> response =
                branchController.updateBranchName(99L, Map.of("name", "Sucursal Nueva"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}