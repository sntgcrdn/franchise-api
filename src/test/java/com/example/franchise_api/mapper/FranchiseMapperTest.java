package com.example.franchise_api.mapper;

import com.example.franchise_api.dto.request.FranchiseRequestDTO;
import com.example.franchise_api.dto.response.FranchiseResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseMapperTest {

    @Test
    void testToResponse_WithBranches() {
        Branch branch1 = Branch.builder().id(1L).name("Sucursal Medellín").build();
        Branch branch2 = Branch.builder().id(2L).name("Sucursal Bogotá").build();

        Franchise franchise = Franchise.builder()
                .id(10L)
                .name("Franquicia Central")
                .owner("Santiago")
                .country("Colombia")
                .branches(List.of(branch1, branch2))
                .build();

        FranchiseResponseDTO dto = FranchiseMapper.toResponse(franchise);

        assertNotNull(dto);
        assertEquals(franchise.getId(), dto.getId());
        assertEquals(franchise.getName(), dto.getName());
        assertEquals(franchise.getOwner(), dto.getOwner());
        assertEquals(franchise.getCountry(), dto.getCountry());
        assertNotNull(dto.getBranches());
        assertEquals(2, dto.getBranches().size());
        assertTrue(dto.getBranches().contains("Sucursal Medellín"));
        assertTrue(dto.getBranches().contains("Sucursal Bogotá"));
    }

    @Test
    void testToResponse_WithoutBranches() {
        Franchise franchise = Franchise.builder()
                .id(11L)
                .name("Franquicia Norte")
                .owner("Ana")
                .country("Colombia")
                .branches(null)
                .build();

        FranchiseResponseDTO dto = FranchiseMapper.toResponse(franchise);

        assertNotNull(dto);
        assertEquals(franchise.getId(), dto.getId());
        assertEquals(franchise.getName(), dto.getName());
        assertEquals(franchise.getOwner(), dto.getOwner());
        assertEquals(franchise.getCountry(), dto.getCountry());
        assertNull(dto.getBranches());
    }

    @Test
    void testToResponse_NullFranchise() {
        FranchiseResponseDTO dto = FranchiseMapper.toResponse(null);
        assertNull(dto);
    }

    @Test
    void testToEntity() {
        FranchiseRequestDTO requestDTO = FranchiseRequestDTO.builder()
                .name("Franquicia Sur")
                .country("Colombia")
                .build();

        Franchise franchise = FranchiseMapper.toEntity(requestDTO);

        assertNotNull(franchise);
        assertEquals(requestDTO.getName(), franchise.getName());
        assertEquals(requestDTO.getCountry(), franchise.getCountry());
    }

    @Test
    void testToEntity_NullDTO() {
        Franchise franchise = FranchiseMapper.toEntity(null);
        assertNull(franchise);
    }
}