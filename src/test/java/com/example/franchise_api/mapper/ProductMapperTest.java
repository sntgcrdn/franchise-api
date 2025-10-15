package com.example.franchise_api.mapper;

import com.example.franchise_api.dto.request.ProductRequestDTO;
import com.example.franchise_api.dto.response.ProductResponseDTO;
import com.example.franchise_api.dto.response.BranchSummaryDTO;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.entity.Branch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void testToResponse_WithBranch() {
        Branch branch = Branch.builder()
                .id(1L)
                .name("Sucursal Central")
                .city("Medellín")
                .build();

        Product product = Product.builder()
                .id(10L)
                .name("Café Especial")
                .price(12000.0)
                .category("Bebida")
                .stock(50)
                .description("Café premium")
                .branch(branch)
                .build();

        ProductResponseDTO dto = ProductMapper.toResponse(product);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Café Especial", dto.getName());
        assertEquals(12000.0, dto.getPrice());
        assertEquals("Bebida", dto.getCategory());
        assertEquals(50, dto.getStock());
        assertEquals("Café premium", dto.getDescription());
        assertEquals("Sucursal Central", dto.getBranchName());

        assertNotNull(dto.getBranch());
        BranchSummaryDTO branchSummary = dto.getBranch();
        assertEquals(1L, branchSummary.getId());
        assertEquals("Sucursal Central", branchSummary.getName());
        assertEquals("Medellín", branchSummary.getCity());
    }

    @Test
    void testToResponse_NullBranch() {
        Product product = Product.builder()
                .id(11L)
                .name("Producto sin sucursal")
                .build();

        ProductResponseDTO dto = ProductMapper.toResponse(product);

        assertNotNull(dto);
        assertEquals(11L, dto.getId());
        assertEquals("Producto sin sucursal", dto.getName());
        assertNull(dto.getBranchName());
        assertNull(dto.getBranch());
    }

    @Test
    void testToResponse_NullProduct() {
        ProductResponseDTO dto = ProductMapper.toResponse(null);
        assertNull(dto);
    }

    @Test
    void testToEntity_Full() {
        Branch branch = Branch.builder()
                .id(1L)
                .name("Sucursal Medellín")
                .city("Medellín")
                .build();

        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Café de origen");
        dto.setPrice(10000.0);
        dto.setCategory("Bebida");
        dto.setStock(30);
        dto.setDescription("Producto premium");

        Product product = ProductMapper.toEntity(dto, branch);

        assertNotNull(product);
        assertEquals("Café de origen", product.getName());
        assertEquals(10000.0, product.getPrice());
        assertEquals("Bebida", product.getCategory());
        assertEquals(30, product.getStock());
        assertEquals("Producto premium", product.getDescription());
        assertEquals(branch, product.getBranch());
    }

    @Test
    void testToEntity_NullDto() {
        Product product = ProductMapper.toEntity(null, new Branch());
        assertNull(product);
    }
}