package com.example.franchise_api.integration;

import com.example.franchise_api.entity.Franchise;
import com.example.franchise_api.repository.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class FranchiseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Test
    void testCreateAndGetFranchise() throws Exception {
        Franchise f = new Franchise();
        f.setName("Nequi Coffee");
        f.setOwner("Santiago");
        f.setCountry("Colombia");
        franchiseRepository.save(f);

        mockMvc.perform(get("/franchises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", hasItem("Nequi Coffee")));
    }
}