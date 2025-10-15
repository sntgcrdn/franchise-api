package com.example.franchise_api.mapper;

import com.example.franchise_api.dto.request.BranchRequestDTO;
import com.example.franchise_api.dto.response.BranchResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchMapperTest {

    @Test
    void testToResponse_WithFranchise() {
        Franchise franchise = Franchise.builder()
                .id(1L)
                .name("Franquicia Central")
                .build();

        Branch branch = Branch.builder()
                .id(10L)
                .name("Sucursal Medellín")
                .city("Medellín")
                .address("Cra 43A #1-50")
                .franchise(franchise)
                .build();

        BranchResponseDTO dto = BranchMapper.toResponse(branch);

        assertNotNull(dto);
        assertEquals(branch.getId(), dto.getId());
        assertEquals(branch.getName(), dto.getName());
        assertEquals(branch.getCity(), dto.getCity());
        assertEquals(branch.getAddress(), dto.getAddress());
        assertEquals(franchise.getName(), dto.getFranchiseName());
        assertNotNull(dto.getFranchise());
        assertEquals(franchise.getId(), dto.getFranchise().getId());
        assertEquals(franchise.getName(), dto.getFranchise().getName());
    }

    @Test
    void testToResponse_WithoutFranchise() {
        Branch branch = Branch.builder()
                .id(11L)
                .name("Sucursal Bogotá")
                .city("Bogotá")
                .address("Calle 50 #10-20")
                .franchise(null)
                .build();

        BranchResponseDTO dto = BranchMapper.toResponse(branch);

        assertNotNull(dto);
        assertEquals(branch.getId(), dto.getId());
        assertEquals(branch.getName(), dto.getName());
        assertEquals(branch.getCity(), dto.getCity());
        assertEquals(branch.getAddress(), dto.getAddress());
        assertNull(dto.getFranchiseName());
        assertNull(dto.getFranchise());
    }

    @Test
    void testToResponse_NullBranch() {
        BranchResponseDTO dto = BranchMapper.toResponse(null);
        assertNull(dto);
    }

    @Test
    void testToEntity() {
        Franchise franchise = Franchise.builder()
                .id(2L)
                .name("Franquicia Norte")
                .build();

        BranchRequestDTO dto = new BranchRequestDTO();
        dto.setName("Sucursal Cali");
        dto.setCity("Cali");
        dto.setAddress("Av. 6 #20-30");

        Branch branch = BranchMapper.toEntity(dto, franchise);

        assertNotNull(branch);
        assertEquals(dto.getName(), branch.getName());
        assertEquals(dto.getCity(), branch.getCity());
        assertEquals(dto.getAddress(), branch.getAddress());
        assertEquals(franchise, branch.getFranchise());
    }

    @Test
    void testToEntity_NullDTO() {
        Branch branch = BranchMapper.toEntity(null, null);
        assertNull(branch);
    }
}