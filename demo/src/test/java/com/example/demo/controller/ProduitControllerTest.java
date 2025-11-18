package com.example.demo.controller;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.service.ProduitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Updated Web Layer Test.
 * Now it tests the controller which uses DTOs.
 */
@WebMvcTest(ProduitController.class)
public class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduitService produitService;

    @MockBean
    private com.example.demo.security.CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(roles = "CLIENT")
    public void testListProduitsAsClient() throws Exception {
        // 1. Setup (Given)
        // Service now returns DTOs, so we mock that
        when(produitService.findAllProduits()).thenReturn(new ArrayList<ProduitDTO>());

        // 2. Action (When) & 3. Assertion (Then)
        mockMvc.perform(get("/produits"))
                .andExpect(status().isOk())
                .andExpect(view().name("produits"))
                .andExpect(model().attributeExists("produits"));
    }
}