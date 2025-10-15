package com.example.franchise_api.integration;

import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.entity.Product;
import com.example.franchise_api.repository.BranchRepository;
import com.example.franchise_api.repository.FranchiseRepository;
import com.example.franchise_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Test
    void testGetProductById() throws Exception {
        Franchise franchise = new Franchise();
        franchise.setName("Nequi Coffee");
        franchise.setOwner("Santiago");
        franchise.setCountry("Colombia");
        franchise = franchiseRepository.save(franchise);

        Branch branch = new Branch();
        branch.setName("Sucursal Medellín");
        branch.setCity("Medellín");
        branch.setFranchise(franchise);
        branch = branchRepository.save(branch);

        Product product = new Product();
        product.setName("Café de origen");
        product.setPrice(12.5);
        product.setStock(50);
        product.setBranch(branch);
        product = productRepository.save(product);

        mockMvc.perform(get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Café de origen")));
    }
}