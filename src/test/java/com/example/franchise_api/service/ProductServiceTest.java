package com.example.franchise_api.service;

import com.example.franchise_api.dto.request.ProductRequestDTO;
import com.example.franchise_api.dto.request.ProductStockDTO;
import com.example.franchise_api.dto.response.ProductResponseDTO;
import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.repository.ProductRepository;
import com.example.franchise_api.repository.BranchRepository;
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

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private ProductService productService;

    private Branch branch;
    private Product product;
    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        branch = Branch.builder()
                .id(1L)
                .name("Sucursal Medellín")
                .city("Medellín")
                .address("Cra 43A #1-50")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Café de origen")
                .description("Aromático y balanceado")
                .price(12000.0)
                .category("Bebidas")
                .branch(branch)
                .stock(10)
                .build();

        requestDTO = ProductRequestDTO.builder()
                .name("Café de origen")
                .description("Aromático y balanceado")
                .price(12000.0)
                .category("Bebidas")
                .branchId(1L)
                .build();
    }

    @Test
    void testGetAllProducts_ReturnsList() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Café de origen", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<ProductResponseDTO> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Café de origen", result.get().getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ProductResponseDTO> result = productService.getProductById(99L);

        assertTrue(result.isEmpty());
        verify(productRepository).findById(99L);
    }

    // ✅ Create product - branch exists
    @Test
    void testCreateProduct_BranchExists() {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Optional<ProductResponseDTO> result = productService.createProduct(requestDTO);

        assertTrue(result.isPresent());
        assertEquals("Café de origen", result.get().getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testCreateProduct_BranchNotFound() {
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProductResponseDTO> result = productService.createProduct(requestDTO);

        assertTrue(result.isEmpty());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testUpdateProduct_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Optional<ProductResponseDTO> result = productService.updateProduct(1L, requestDTO);

        assertTrue(result.isPresent());
        assertEquals("Café de origen", result.get().getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ProductResponseDTO> result = productService.updateProduct(99L, requestDTO);

        assertTrue(result.isEmpty());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testUpdateProductStock() {
        ProductStockDTO stockDTO = new ProductStockDTO();
        stockDTO.setStock(20);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Optional<ProductResponseDTO> result = productService.updateProductStock(1L, stockDTO);

        assertTrue(result.isPresent());
        assertEquals(20, result.get().getStock()); // from mock product
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProductName() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Optional<ProductResponseDTO> result = productService.updateProductName(1L, "Nuevo nombre");

        assertTrue(result.isPresent());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Exists() {
        when(productRepository.existsById(1L)).thenReturn(true);

        boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById(99L)).thenReturn(false);

        boolean result = productService.deleteProduct(99L);

        assertFalse(result);
        verify(productRepository, never()).deleteById(any());
    }
}