package com.example.franchise_api.controller;

import com.example.franchise_api.dto.request.ProductRequestDTO;
import com.example.franchise_api.dto.request.ProductStockDTO;
import com.example.franchise_api.dto.response.BranchSummaryDTO;
import com.example.franchise_api.dto.response.ProductResponseDTO;
import com.example.franchise_api.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductResponseDTO productResponse;

    private BranchSummaryDTO branchSummary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchSummary = BranchSummaryDTO.builder()
                .id(1L)
                .name("Sucursal Medellín")
                .city("Medellín")
                .build();

        productResponse = ProductResponseDTO.builder()
                .id(1L)
                .name("Café de origen")
                .description("Café suave de Colombia")
                .price(25000.0)
                .category("Bebidas")
                .stock(10)
                .branch(branchSummary)
                .build();
    }

    @Test
    void testGetAllProducts_ReturnsList_DirectList() {
        when(productService.getAllProducts()).thenReturn(List.of(productResponse));
    
        List<ProductResponseDTO> response = productController.getAllProducts();
    
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Café de origen", response.get(0).getName());
    
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Found() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponseDTO> response = productController.getProductById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Café de origen", Objects.requireNonNull(response.getBody()).getName());
        verify(productService).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById(99L)).thenReturn(Optional.empty());
    
        ResponseEntity<ProductResponseDTO> response = productController.getProductById(99L);
    
        assertEquals(404, response.getStatusCode().value());
        verify(productService).getProductById(99L);
    }

    @Test
    void testCreateProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Nuevo producto");
        dto.setBranchId(1L);

        when(productService.createProduct(dto)).thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponseDTO> response = productController.createProduct(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Café de origen", Objects.requireNonNull(response.getBody()).getName());
        verify(productService).createProduct(dto);
    }

    @Test
    void testUpdateProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Producto actualizado");

        when(productService.updateProduct(1L, dto)).thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponseDTO> response = productController.updateProduct(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Café de origen", Objects.requireNonNull(response.getBody()).getName());
        verify(productService).updateProduct(1L, dto);
    }

    @Test
    void testUpdateProduct_NotFound() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Producto inexistente");

        when(productService.updateProduct(99L, dto)).thenReturn(Optional.empty());

        ResponseEntity<ProductResponseDTO> response = productController.updateProduct(99L, dto);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody(), "Response body should be null for not found");
        verify(productService).updateProduct(99L, dto);
    }

    @Test
    void testUpdateStock_Success() {
        ProductStockDTO stockDTO = new ProductStockDTO();
        stockDTO.setStock(15);

        when(productService.updateProductStock(1L, stockDTO))
                .thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponseDTO> response = productController.updateStock(1L, stockDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Café de origen", Objects.requireNonNull(response.getBody()).getName());
        verify(productService).updateProductStock(1L, stockDTO);
    }

    @Test
    void testUpdateStock_NotFound() {
        ProductStockDTO stockDTO = new ProductStockDTO();
        stockDTO.setStock(15);

        when(productService.updateProductStock(99L, stockDTO))
                .thenReturn(Optional.empty());

        ResponseEntity<ProductResponseDTO> response = productController.updateStock(99L, stockDTO);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody(), "Response body should be null when product not found");
        verify(productService).updateProductStock(99L, stockDTO);
    }

    @Test
    void testUpdateProductName_Success() {
        when(productService.updateProductName(1L, "Café Especial"))
                .thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponseDTO> response =
                productController.updateProductName(1L, Map.of("name", "Café Especial"));

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Café de origen", Objects.requireNonNull(response.getBody()).getName());
        verify(productService).updateProductName(1L, "Café Especial");
    }

    @Test
    void testUpdateProductName_NotFound() {
        when(productService.updateProductName(99L, "No existe"))
                .thenReturn(Optional.empty());

        ResponseEntity<ProductResponseDTO> response =
                productController.updateProductName(99L, Map.of("name", "No existe"));

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody(), "Response body should be null when product not found");
        verify(productService).updateProductName(99L, "No existe");
    }

    @Test
    void testDeleteProduct_Success() {
        when(productService.deleteProduct(1L)).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCode().value()); // No Content
        assertNull(response.getBody());
        verify(productService).deleteProduct(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productService.deleteProduct(99L)).thenReturn(false);

        ResponseEntity<Void> response = productController.deleteProduct(99L);

        assertEquals(404, response.getStatusCode().value()); // Not Found
        assertNull(response.getBody());
        verify(productService).deleteProduct(99L);
    }
}