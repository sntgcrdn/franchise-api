package com.example.franchise_api.integration;

import com.example.franchise_api.entity.Branch;
import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.repository.BranchRepository;
import com.example.franchise_api.repository.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class BranchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Test
    void testCreateAndGetBranch() throws Exception {
        // Create a franchise first
        Franchise franchise = new Franchise();
        franchise.setName("Nequi Coffee");
        franchise.setOwner("Santiago");
        franchise.setCountry("Colombia");
        franchise = franchiseRepository.save(franchise);

        // Create a branch linked to that franchise
        Branch branch = new Branch();
        branch.setName("Sucursal Medellín");
        branch.setCity("Medellín");
        branch.setAddress("Cra 43A #1-50");
        branch.setFranchise(franchise);
        branchRepository.save(branch);

        // Verify GET /branches returns it
        mockMvc.perform(get("/branches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", hasItem("Sucursal Medellín")))
                .andExpect(jsonPath("$[*].franchiseName", hasItem("Nequi Coffee")));
    }

    @Test
    void testGetBranchById() throws Exception {
        Franchise franchise = new Franchise();
        franchise.setName("Nequi Coffee");
        franchise.setOwner("Santiago");
        franchise.setCountry("Colombia");
        franchise = franchiseRepository.save(franchise);

        Branch branch = new Branch();
        branch.setName("Sucursal Bogotá");
        branch.setCity("Bogotá");
        branch.setAddress("Calle 80 #15-30");
        branch.setFranchise(franchise);
        branch = branchRepository.save(branch);

        mockMvc.perform(get("/branches/" + branch.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Sucursal Bogotá")))
                .andExpect(jsonPath("$.franchiseName", is("Nequi Coffee")));
    }
}